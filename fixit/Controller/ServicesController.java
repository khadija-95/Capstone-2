package com.example.fixit.Controller;

import com.example.fixit.Api.ApiResponse;
import com.example.fixit.Model.Services;
import com.example.fixit.Service.ServicesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServicesController {

    private final ServicesService servicesService;

    @GetMapping("/get")
    public ResponseEntity getAllServices(){

        return ResponseEntity.status(200).body(servicesService.getAllServices());

    }

    @PostMapping("/add")
    public ResponseEntity addService(@Valid @RequestBody Services services, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        servicesService.addService(services);
        return ResponseEntity.status(200).body(new ApiResponse("Success"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateServices(@PathVariable Integer id , @RequestBody @Valid Services services, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean isUpdate =servicesService.updateServices(services,id);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Services updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteServices(@PathVariable Integer id){
        boolean isDelete = servicesService.deleteServices(id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Services deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @GetMapping("/search")
    public ResponseEntity searchServices(@RequestParam String keyword) {
        return ResponseEntity.status(200).body(servicesService.searchServices(keyword));
    }

}
