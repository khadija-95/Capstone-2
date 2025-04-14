package com.example.fixit.Controller;

import com.example.fixit.Api.ApiResponse;
import com.example.fixit.Model.ServiceProvider;
import com.example.fixit.Service.ServiceProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService providersService;

    @GetMapping("/get")
    public ResponseEntity getAllProviders(){

        return ResponseEntity.status(200).body(providersService.getAllProviders());

    }

    @PostMapping("/add")
    public ResponseEntity addProviders(@Valid @RequestBody ServiceProvider providers, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        providersService.addProviders(providers);
        return ResponseEntity.status(200).body(new ApiResponse("Success"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateProviders(@PathVariable Integer id , @RequestBody @Valid ServiceProvider providers, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean isUpdate =providersService.updateProviders(providers,id);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Providers updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProviders(@PathVariable Integer id){
        boolean isDelete = providersService.deleteProviders(id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Provider deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @GetMapping("/search-by-experience")
    public ResponseEntity searchByExperience(@RequestParam Integer minYears){
        List<ServiceProvider>providers=providersService.searchByExperience(minYears);
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/top-rated")
    public ResponseEntity getTopRatedProviders() {
        return ResponseEntity.ok(providersService.getTopRatedProviders());
    }




}
