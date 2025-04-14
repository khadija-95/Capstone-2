package com.example.fixit.Controller;

import com.example.fixit.Api.ApiResponse;
import com.example.fixit.Model.Request;
import com.example.fixit.Model.Services;
import com.example.fixit.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/get")
    public ResponseEntity getAllRequests(){

        return ResponseEntity.status(200).body(requestService.getAllRequests());

    }

    @PostMapping("/add")
    public ResponseEntity addRequest(@Valid @RequestBody Request request, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        requestService.addRequest(request);
        return ResponseEntity.status(200).body(new ApiResponse("Success"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateRequest(@PathVariable Integer id , @RequestBody @Valid Request request, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean isUpdate =requestService.updateRequest(request,id);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Request updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRequest(@PathVariable Integer id){
        boolean isDelete = requestService.deleteRequest(id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Request deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @PostMapping("/emergency")
    public ResponseEntity createEmergency(@RequestBody @Valid Request request) {
        String result = requestService.createEmergencyRequest(request);
        if (result.startsWith("No available")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.status(201).body(result);
    }

    @DeleteMapping("/cancel/{requestId}/{customerId}")
    public ResponseEntity cancel(@PathVariable Integer requestId, @PathVariable  Integer customerId) {
        String result = requestService.cancelRequest(requestId, customerId);
        if (result.startsWith("Only") || result.startsWith("Request not")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reminder/{requestId}")
    public ResponseEntity getReminder(@PathVariable Integer requestId) {
        String result = requestService.getScheduledReminder(requestId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/most-requested-service")
    public ResponseEntity getMostRequestedService() {
        Services services = requestService.getMostRequestedService();
        if (services==null)return ResponseEntity.ok("no service requested yet");
        return ResponseEntity.ok(services);
    }

    @GetMapping("/completed-count/{providerId}")
    public ResponseEntity getCompletedCount(@PathVariable Integer providerId) {
        int count = requestService.getCompletedRequestsByProvider(providerId);
        return ResponseEntity.ok("Completed requests for provider: " + count);
    }

    @GetMapping("/suggested-services/{customerId}")
    public ResponseEntity getSuggestedServices(@PathVariable Integer customerId) {
        List<Services> suggestions = requestService.getSuggestedServicesForUser(customerId);
        if (suggestions.isEmpty()) {
            return ResponseEntity.ok("No suggestions available for this user.");
        }
        return ResponseEntity.ok(suggestions);
    }

    @PutMapping("/assign/{requestId}/{providerId}")
    public ResponseEntity assignRequest(@PathVariable Integer requestId, @PathVariable Integer providerId) {
        String result = requestService.assignRequestToProvider(requestId, providerId);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/complete/{requestId}")
    public ResponseEntity completeRequest(@PathVariable Integer requestId) {
        String result = requestService.markRequestAsCompleted(requestId);
        if (result.contains("not found")) {
            return ResponseEntity.status(404).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/status")
    public ResponseEntity updateRequestStatus(@RequestParam Integer requestId, @RequestParam String newStatus) {
        String result = requestService.updateRequestStatus(requestId, newStatus);
        if (result.contains("not found") || result.contains("Invalid")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pending")
    public ResponseEntity getPendingRequests() {
        List<Request> pending = requestService.getPendingRequests();
        if (pending.isEmpty()) {
            return ResponseEntity.ok("No pending requests found.");
        }
        return ResponseEntity.ok(pending);
    }



}
