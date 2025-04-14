package com.example.fixit.Repository;

import com.example.fixit.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {

    Request findRequestById(Integer id);

    int countByProviderIdAndStatus(Integer providerId, String status);

    List<Request> findByCustomerId(Integer customerId);

}
