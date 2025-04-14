package com.example.fixit.Service;

import com.example.fixit.Model.*;
import com.example.fixit.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final ServiceProviderRepository providerRepository;
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final ServicesRepository servicesRepository;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    //5 request service
    public void addRequest(Request request) {
        request.setScheduledTime(LocalDateTime.now());
        requestRepository.save(request);
    }

    public Boolean updateRequest(Request request, Integer id) {
        Request oldRequest = requestRepository.findRequestById(id);
        if (oldRequest == null) {
            return false;
        }
        oldRequest.setDescription(request.getDescription());
        oldRequest.setStatus(request.getStatus());
        requestRepository.save(oldRequest);
        return true;
    }

    public Boolean deleteRequest(Integer id) {
        Request request = requestRepository.findRequestById(id);
        if (request == null) {
            return false;
        }
        requestRepository.delete(request);
        return true;
    }

    //6 emergency request
    public String createEmergencyRequest(Request request) {
        if (!request.isEmergency()) {
            return "Request must be marked as emergency";
        }

        List<ServiceProvider> availableProviders = providerRepository.findByIsAvailableTrue();

        if (availableProviders.isEmpty()) {
            return "No available providers at the moment";
        }

        ServiceProvider selectedProvider = availableProviders.get(0); // نختار أول واحد متاح

        request.setProviderId(selectedProvider.getId());
        request.setStatus("EMERGENCY");

        requestRepository.save(request);

        return "Emergency request created and assigned to provider: " + selectedProvider.getName();
    }

    //7 cancel Request if it is PENDING
    public String cancelRequest(Integer requestId, Integer customerId) {
        Request request = requestRepository.getById(requestId);
        if (request == null || !request.getCustomerId().equals(customerId)) {
            return "Request not found or user mismatch";
        }

        if (!request.getStatus().equalsIgnoreCase("PENDING")) {
            return "Only pending requests can be canceled";
        }

        requestRepository.delete(request);
        return "Request canceled";
    }

    //8 Scheduled Order Alert (Service Alert After 1 Hour)
    public String getScheduledReminder(Integer requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) return "Request not found";

        LocalDateTime scheduledTime = request.getScheduledTime();
        if (scheduledTime == null) return "No scheduled time for this request";

        LocalDateTime now = LocalDateTime.now();

        if (scheduledTime.isBefore(now)) {
            return "The request time has already passed.";
        }

        long minutes = Duration.between(now, scheduledTime).toMinutes();

        if (minutes <= 60) {
            return "Reminder: Your scheduled request will start in " + minutes + " minutes.";
        }

        return "No reminder needed. The request is more than 1 hour away.";
    }

    //9 get most requested services
    public Services getMostRequestedService() {
        List<Request> requests = requestRepository.findAll();
        List<Services> services = servicesRepository.findAll();

        Services mostRequestedService = null;
        int maxCount = 0;

        for (Services s : services) {
            int count = 0;
            for (Request r : requests) {
                if (s.getId().equals(r.getServicesId())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                mostRequestedService = s;
            }
        }

        return mostRequestedService;
    }


    //10 Number of completed requests for the service provider
    public int getCompletedRequestsByProvider(Integer providerId) {
        return requestRepository.countByProviderIdAndStatus(providerId, "COMPLETED");
    }

    //11 Suggest recurring services to the user
    public List<Services> getSuggestedServicesForUser(Integer customerId) {
        List<Request> customerRequests = requestRepository.findByCustomerId(customerId);
        List<Services> allServices = servicesRepository.findAll();

        List<Services> suggestions = new ArrayList<>();

        for (Services service : allServices) {
            int count = 0;
            for (Request request : customerRequests) {
                if (service.getId().equals(request.getServicesId())) {
                    count++;
                }
            }
            if (count > 1) {
                suggestions.add(service);
            }
        }

        return suggestions;
    }

    //12 Assign Request to Service Provider
    public String assignRequestToProvider(Integer requestId, Integer providerId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) return "Request not found";

        ServiceProvider provider = providerRepository.findById(providerId).orElse(null);
        if (provider == null) return "Provider not found";

        request.setProviderId(providerId);
        request.setStatus("IN_PROGRESS");

        requestRepository.save(request);

        return "Request assigned to provider: " + provider.getName();
    }

    //13 Change the order status to "COMPLETED" and update the service provider.
    public String markRequestAsCompleted(Integer requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) return "Request not found";

        request.setStatus("COMPLETED");
        requestRepository.save(request);

        Integer providerId = request.getProviderId();
        if (providerId != null) {
            ServiceProvider provider = providerRepository.findById(providerId).orElse(null);
            if (provider != null) {
                provider.setIsAvailable(true);
                providerRepository.save(provider);
            }
        }

        return "Request marked as COMPLETED";
    }

    //14 update Request status
    public String updateRequestStatus(Integer requestId, String newStatus) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if (request == null) return "Request not found";

        List<String> allowedStatuses = Arrays.asList(
                "PENDING", "IN_PROGRESS", "COMPLETED", "CANCELLED", "EMERGENCY"
        );

        if (!allowedStatuses.contains(newStatus.toUpperCase())) {
            return "Invalid status value";
        }

        request.setStatus(newStatus.toUpperCase());
        requestRepository.save(request);

        return "Request status updated to: " + newStatus.toUpperCase();
    }

    //15 View pending orders
    public List<Request> getPendingRequests() {
        List<Request> all = requestRepository.findAll();
        List<Request> pending = new ArrayList<>();

        for (Request r : all) {
            if ("PENDING".equalsIgnoreCase(r.getStatus())) {
                pending.add(r);
            }
        }

        return pending;
    }
}
