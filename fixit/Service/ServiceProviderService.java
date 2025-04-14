package com.example.fixit.Service;

import com.example.fixit.Model.Request;
import com.example.fixit.Model.Review;
import com.example.fixit.Model.ServiceProvider;
import com.example.fixit.Repository.RequestRepository;
import com.example.fixit.Repository.ReviewRepository;
import com.example.fixit.Repository.ServiceProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceProviderService {
    private final ServiceProviderRepository providersRepository;
    private final ReviewRepository reviewRepository;
    private final RequestRepository requestRepository;


    public List<ServiceProvider> getAllProviders(){
        return providersRepository.findAll();
    }

    public void addProviders(ServiceProvider providers){
        providers.setIsActive(true);
        providers.setIsApproved(false);
        providers.setIsAvailable(true);

        providersRepository.save(providers);
    }

    public Boolean updateProviders(ServiceProvider providers, Integer id){
        ServiceProvider oldProviders = providersRepository.findProviderById(id);
        if (oldProviders==null){
            return false;
        }
        oldProviders.setName(providers.getName());
        oldProviders.setEmail(providers.getEmail());
        oldProviders.setPhone(providers.getPhone());
        oldProviders.setPassword(providers.getPassword());
        oldProviders.setSpecialization(providers.getSpecialization());
        oldProviders.setLicenseNumber(providers.getLicenseNumber());
        oldProviders.setNationalId(providers.getNationalId());
        providersRepository.save(oldProviders);
        return true;

    }

    public Boolean deleteProviders(Integer id){
        ServiceProvider providers=providersRepository.findProviderById(id);
        if (providers==null){
            return false;
        }
        providersRepository.delete(providers);
        return true;
    }

    //16 Search for service providers by years of experience
    public List<ServiceProvider>searchByExperience(Integer years){
        return providersRepository.findServiceProviderByYearsOfExperience(years);
    }


    //17 Showcasing the best rated service providers
    public List<ServiceProvider> getTopRatedProviders() {
        List<ServiceProvider> allProviders = providersRepository.findAll();
        List<Review> allReviews = reviewRepository.findAll();
        List<Request> allRequests = requestRepository.findAll();

        List<ServiceProvider> ratedProviders = new ArrayList<>();

        for (ServiceProvider provider : allProviders) {
            int total = 0;
            int count = 0;

            for (Request request : allRequests) {
                if (provider.getId().equals(request.getProviderId())) {
                    for (Review review : allReviews) {
                        if (review.getRequestId().equals(request.getId())) {
                            total += review.getRating();
                            count++;
                        }
                    }
                }
            }

            if (count > 0) {
                double average = (double) total / count;

                // نضيف فقط اللي عندهم تقييم، ونخزّن المتوسط مؤقتًا في حقل غير محفوظ
                provider.setName(provider.getName() + " - Rating: " + average); // نخزنها مؤقتًا في الاسم للعرض
                ratedProviders.add(provider);
            }
        }

        // ترتيب تنازلي على حسب التقييم داخل الاسم (بما إنه مؤقت)
        ratedProviders.sort((a, b) -> {
            double r1 = Double.parseDouble(a.getName().split(": ")[1]);
            double r2 = Double.parseDouble(b.getName().split(": ")[1]);
            return Double.compare(r2, r1);
        });

        return ratedProviders.size() > 5 ? ratedProviders.subList(0, 5) : ratedProviders;
    }

}
