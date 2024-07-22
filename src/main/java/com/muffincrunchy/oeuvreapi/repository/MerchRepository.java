package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.Merch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface MerchRepository extends JpaRepository<Merch, String> {

    @Query(value = "SELECT * FROM merchs", nativeQuery = true)
    List<Merch> getAllMerchs();

    @Query(value = "SELECT * FROM merchs WHERE id = :id", nativeQuery = true)
    Optional<Merch> getMerchById(@Param("id") String id);

    @Modifying
    @Query(value = "INSERT INTO merchs (id, name, category_id, price, stock, artist_id, type_id) VALUES (:id, :name, :category_id, :price, :stock, :artist_id, :type_id)", nativeQuery = true)
    int createMerch(@Param("id") String id, @Param("name") String name, @Param("category_id") String categoryId, @Param("price") Long price, @Param("stock") Integer stock, @Param("artist_id") String artist_id, @Param("type_id") String type_id);

    @Modifying
    @Query(value = "INSERT INTO merchs_payments (merch_id, payments_id) VALUES (:merch_id, :payments_id)", nativeQuery = true)
    int createPayment(@Param("merch_id") String merchId, @Param("payments_id") String paymentsId);

    @Modifying
    @Query(value = "INSERT INTO merchs_shipments (merch_id, shipments_id) VALUES (:merch_id, :shipments_id)", nativeQuery = true)
    int createShipment(@Param("merch_id") String merchId, @Param("shipments_id") String shipmentsId);

    @Modifying
    @Query(value = "UPDATE merchs SET name = :name, category_id = :category_id, price = :price, stock = :stock, artist_id = :artist_id, type_id = :type_id WHERE id = :id", nativeQuery = true)
    int updateMerchById(@Param("id") String id, @Param("name") String name, @Param("category_id") String categoryId, @Param("price") Long price, @Param("stock") Integer stock, @Param("artist_id") String artist_id, @Param("type_id") String type_id);

    @Modifying
    @Query(value = "DELETE FROM merchs WHERE id = :id", nativeQuery = true)
    void deleteMerchById(@Param("id") String id);
}
