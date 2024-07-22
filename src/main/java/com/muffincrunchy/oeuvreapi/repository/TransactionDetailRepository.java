package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {

    @Modifying
    @Query(value = "INSERT INTO transaction_details (id, trans_id, merch_id, shipment_id, merch_price, shipment_fee, qty, payment_id) VALUES (:id, :trans_id, :merch_id, :shipment_id, :merch_price, :shipment_fee, :qty, :payment_id)", nativeQuery = true)
    int createTransactionDetail(@Param("id") String id, @Param("trans_id") String transId, @Param("merch_id") String merchId, @Param("shipment_id") String shipmentId, @Param("merch_price") Long merchPrice, @Param("shipment_fee") Long shipmentFee, @Param("qty") Integer qty, @Param("payment_id") String paymentId);
}
