package com.example.fixit.Controller;


import com.example.fixit.Api.ApiResponse;
import com.example.fixit.Model.Admin;
import com.example.fixit.Service.AdminService;
import com.example.fixit.Service.ServiceProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ServiceProviderService providerService;

    @GetMapping("/get")
    public ResponseEntity getAllAdmins(){

        return ResponseEntity.status(200).body(adminService.getAllAdmins());

    }

    @PostMapping("/add")
    public ResponseEntity addAllAdmins(@Valid @RequestBody Admin admin, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        adminService.addAllAdmins(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Success"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id , @RequestBody @Valid Admin admin, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean isUpdate =adminService.updateAdmin(id, admin);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Admin updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable Integer id){
        boolean isDelete = adminService.deleteAdmin(id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Admin deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @GetMapping("/review-provider/{providerId}")
    public ResponseEntity reviewProvider(@PathVariable Integer providerId) {
        String result = adminService.reviewProviderInfo(providerId);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/dashboard")
    public ResponseEntity getDashboard() {
        String dashboard = adminService.getAdminDashboard();
        return ResponseEntity.ok(dashboard);
    }

    @PutMapping("/deactivate-provider/{providerId}")
    public ResponseEntity deactivateProvider(@PathVariable Integer providerId) {
        String result = adminService.deactivateProvider(providerId);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }


}
