package com.example.fixit.Controller;

import com.example.fixit.Api.ApiResponse;
import com.example.fixit.Model.Review;
import com.example.fixit.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity getAllReview(){

        return ResponseEntity.status(200).body(reviewService.getAllReview());

    }

    @PostMapping("/add")
    public ResponseEntity addReview(@Valid @RequestBody Review review, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        reviewService.addReview(review);
        return ResponseEntity.status(200).body(new ApiResponse("Success"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@PathVariable Integer id , @RequestBody @Valid Review review, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean isUpdate =reviewService.updateReview(review,id);
        if (isUpdate){
            return ResponseEntity.status(200).body(new ApiResponse("Review updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id){
        boolean isDelete = reviewService.deleteReview(id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Review deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Not found"));
    }


}
