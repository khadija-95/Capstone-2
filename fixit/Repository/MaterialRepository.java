package com.example.fixit.Repository;

import com.example.fixit.Model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Integer> {
    Material findMaterialById(Integer id);
    List<Material> findMaterialByRequestId(Integer requestId);

    Material[] getMaterialsByRequestId(Integer requestId);
}
