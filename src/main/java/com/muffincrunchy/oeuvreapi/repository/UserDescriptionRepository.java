package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.UserDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDescriptionRepository extends JpaRepository<UserDescription, String> {
}
