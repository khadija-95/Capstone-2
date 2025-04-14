package com.example.fixit.Controller;

import com.example.fixit.Api.ApiResponse;
import com.example.fixit.Model.Customer;
import com.example.fixit.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getAllCustomers(){

        return ResponseEntity.status(200).body(customerService.getAllCustomers());

    }

    @PostMapping("/add")
    public ResponseEntity addAllCustomers(@Valid @RequestBody Customer customer, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        customerService.addAllCustomers(customer);
        return ResponseEntity.status(200).body(new ApiResponse("Success"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id , @RequestBody @Valid Customer customer, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean isUpdate =customerService.updateCustomer(id, customer);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Customer updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer id){
        boolean isDelete = customerService.deleteCustomer(id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Customer deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }
}
