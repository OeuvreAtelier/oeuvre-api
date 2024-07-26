package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, String> {
}
