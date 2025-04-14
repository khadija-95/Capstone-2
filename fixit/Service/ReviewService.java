package com.example.fixit.Service;

import com.example.fixit.Model.Customer;
import com.example.fixit.Model.Request;
import com.example.fixit.Model.Review;
import com.example.fixit.Repository.CustomerRepository;
import com.example.fixit.Repository.RequestRepository;
import com.example.fixit.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final RequestRepository requestRepository;

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }

    public String addReview(Review review){
        Customer customer =customerRepository.findCustomerById(review.getCustomerId());
        Request request = requestRepository.findRequestById(review.getRequestId());
        if (customer==null){
            return "Review not found";
        }
        if (request==null){
            return "Review not found";
        }
        reviewRepository.save(review);
        return "Review added Successfully";
    }

    public Boolean updateReview(Review review,Integer id){
        Review oldReview=reviewRepository.findReviewById(id);
        if (oldReview==null){
            return false;
        }
        oldReview.setRating(review.getRating());
        oldReview.setComment(review.getComment());
        reviewRepository.save(oldReview);
        return true;
    }
    public Boolean deleteReview(Integer id){
        Review review =reviewRepository.findReviewById(id);
        if (review==null){
            return false;
        }
        reviewRepository.delete(review);
        return true;
    }


}
