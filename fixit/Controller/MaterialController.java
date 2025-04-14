package com.example.fixit.Controller;

import com.example.fixit.Api.ApiResponse;
import com.example.fixit.Model.Customer;
import com.example.fixit.Model.Material;
import com.example.fixit.Service.CustomerService;
import com.example.fixit.Service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping("/get")
    public ResponseEntity getAllMaterials(){

        return ResponseEntity.status(200).body(materialService.getAllMaterials());

    }

    @PostMapping("/add")
    public ResponseEntity addAllMaterials(@Valid @RequestBody Material material, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        materialService.addAllMaterials(material);
        return ResponseEntity.status(200).body(new ApiResponse("Success"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMaterial(@PathVariable Integer id , @RequestBody @Valid Material material, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean isUpdate =materialService.updateMaterial(id, material);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Material updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMaterial(@PathVariable Integer id){
        boolean isDelete = materialService.deleteMaterial(id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Material deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @PutMapping("/update-total/{requestId}")
    public ResponseEntity updateTotalCost(@PathVariable Integer requestId){
        materialService.updateRequestTotal(requestId);
        return ResponseEntity.ok("Total cost updated for request Id"+ requestId);
    }
}
