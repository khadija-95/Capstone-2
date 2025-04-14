package com.example.fixit.Service;


import com.example.fixit.Model.Admin;
import com.example.fixit.Model.ServiceProvider;
import com.example.fixit.Repository.AdminRepository;
import com.example.fixit.Repository.CustomerRepository;
import com.example.fixit.Repository.RequestRepository;
import com.example.fixit.Repository.ServiceProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ServiceProviderRepository providerRepository;
    private final CustomerRepository customerRepository;
    private final RequestRepository requestRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void addAllAdmins(Admin admin) {

        adminRepository.save(admin);
    }

    public Boolean updateAdmin(Integer id, Admin admin) {
        Admin oldAdmin = adminRepository.findAdminsById(id);
        if (oldAdmin == null) {
            return false;
        }
        oldAdmin.setUserName(admin.getUserName());
        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setPassword(admin.getPassword());
        adminRepository.save(oldAdmin);
        return true;
    }

    public Boolean deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminsById(id);
        if (admin==null){
            return false;
        }
        adminRepository.delete(admin);
        return true;
    }


    //1 View service provider data to verify before approval
    public String reviewProviderInfo(Integer providerId) {
        ServiceProvider provider = providerRepository.findById(providerId).orElse(null);
        if (provider == null) return "Provider not found";

        return "Name: " + provider.getName() +
                ", Specialization: " + provider.getSpecialization() +
                ", National ID: " + provider.getNationalId() +
                ", License Number: " + provider.getLicenseNumber() +
                ", Is Approved: " + provider.getIsApproved();
    }


    //2 dashboard that gives a quick and useful summary for the admin.
    public String getAdminDashboard() {
        long totalCustomers = customerRepository.count();
        long totalProviders = providerRepository.count();
        long totalRequests = requestRepository.count();

        long pendingRequests = requestRepository.findAll().stream()
                .filter(r -> "PENDING".equalsIgnoreCase(r.getStatus()))
                .count();

        long unapprovedProviders = providerRepository.findAll().stream()
                .filter(p -> !Boolean.TRUE.equals(p.getIsApproved()))
                .count();

        return "Dashboard:\n"
                + "Total Users: " + totalCustomers + "\n"
                + "Total Providers: " + totalProviders + "\n"
                + "Total Requests: " + totalRequests + "\n"
                + "Pending Requests: " + pendingRequests + "\n"
                + "Unapproved Providers: " + unapprovedProviders;
    }

    //3 To stop the service provider's activity
    public String deactivateProvider(Integer providerId) {
        ServiceProvider provider = providerRepository.findById(providerId).orElse(null);
        if (provider == null) return "Provider not found";

        provider.setIsActive(false);
        provider.setIsAvailable(false);

        providerRepository.save(provider);

        return "Provider has been deactivated";
    }


}
