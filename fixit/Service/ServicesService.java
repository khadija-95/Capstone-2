package com.example.fixit.Service;

import com.example.fixit.Model.Request;
import com.example.fixit.Model.Services;
import com.example.fixit.Repository.RequestRepository;
import com.example.fixit.Repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesService {

    private final ServicesRepository servicesRepository;
    private final RequestRepository requestRepository;

    public List<Services>getAllServices(){
        return servicesRepository.findAll();
    }

    public void addService(Services services){
        servicesRepository.save(services);
    }

    public Boolean updateServices(Services services,Integer id){
        Services oldService=servicesRepository.findServicesById(id);
        if (oldService==null){
            return false;
        }
        oldService.setName(services.getName());
        oldService.setDescription(services.getDescription());
        oldService.setPrice(services.getPrice());
        servicesRepository.save(oldService);
        return true;
    }

    public Boolean deleteServices(Integer id){
        Services services=servicesRepository.findServicesById(id);
        if (services==null){
            return false;
        }
        servicesRepository.delete(services);
        return true;
    }

    //18 search services by keyword
    public List<Services> searchServices(String keyword) {
        List<Services> all = servicesRepository.findAll();
        List<Services> result = new ArrayList<>();

        for (Services s : all) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    s.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(s);
            }
        }

        return result;
    }

}
