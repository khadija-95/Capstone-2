package com.example.fixit.Repository;

import com.example.fixit.Model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Integer> {
    ServiceProvider findProviderById(Integer id);

    List<ServiceProvider> findByIsAvailableTrue();

    @Query("SELECT p FROM ServiceProvider p WHERE p.YearsOfExperience =?1")
    List<ServiceProvider> findServiceProviderByYearsOfExperience(Integer years);
}
