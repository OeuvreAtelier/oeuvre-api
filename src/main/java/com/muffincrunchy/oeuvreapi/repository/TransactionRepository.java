package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = "SELECT * FROM transactions", nativeQuery = true)
    List<Transaction> getAllTransactions();

    @Modifying
    @Query(value = "INSERT INTO transactions (id, customer_id, trans_date) VALUES (:id, :customer_id, :trans_date)", nativeQuery = true)
    int createTransaction(@Param("id") String id, @Param("customer_id") String customerId, @Param("trans_date") Date transDate);
}
