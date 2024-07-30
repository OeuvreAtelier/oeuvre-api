package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    @Modifying
    @Query(value = "UPDATE payments SET transaction_status = :transaction_status WHERE id = :id", nativeQuery = true)
    void updateTransactionStatus(@Param("id") String id, @Param("transaction_status") String transaction_status);
}
