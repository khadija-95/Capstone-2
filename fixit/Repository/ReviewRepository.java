package com.example.fixit.Repository;

import com.example.fixit.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Review findReviewById(Integer id);

    Review findReviewByRequestId(Integer requestId);
}
