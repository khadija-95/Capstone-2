package com.example.fixit.Service;


import com.example.fixit.Model.Material;
import com.example.fixit.Model.Request;
import com.example.fixit.Model.Services;
import com.example.fixit.Repository.MaterialRepository;
import com.example.fixit.Repository.RequestRepository;
import com.example.fixit.Repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final RequestRepository requestRepository;
    private final ServicesRepository servicesRepository;

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public void addAllMaterials(Material material) {
        materialRepository.save(material);
        updateRequestTotal(material.getRequestId());
    }

    public Boolean updateMaterial(Integer id, Material material) {
        Material oldMaterial = materialRepository.findMaterialById(id);
        if (oldMaterial == null) {
            return false;
        }
        oldMaterial.setName(material.getName());
        oldMaterial.setQuantity(material.getQuantity());
        oldMaterial.setUnitPrice(material.getUnitPrice());
        materialRepository.save(oldMaterial);
        updateRequestTotal(material.getRequestId());
        return true;
    }

    public Boolean deleteMaterial(Integer id){
        Material material = materialRepository.findMaterialById(id);
        if (material==null){
            return false;
        }
        Integer requestId = material.getRequestId();
        materialRepository.delete(material);
        updateRequestTotal(requestId);
        return true;
    }

    //4 update price after add material
    public void updateRequestTotal(Integer requestId) {
        Request request = requestRepository.getById(requestId);
        if (request == null) return;

        double materialsTotal = 0;
        for (Material m : materialRepository.getMaterialsByRequestId((requestId))){
            materialsTotal += m.getTotalPrice();
        }

        Services service = servicesRepository.getById(request.getServicesId());
        if (service == null) return;

        request.setTotalCost(service.getPrice() + materialsTotal);
    }

}


