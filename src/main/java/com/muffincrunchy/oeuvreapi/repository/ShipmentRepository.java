package com.muffincrunchy.oeuvreapi.repository;

import com.muffincrunchy.oeuvreapi.model.entity.Shipment;
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
public interface ShipmentRepository extends JpaRepository<Shipment, String> {

    @Query(value = "SELECT * FROM shipments", nativeQuery = true)
    List<Shipment> getAllShipments();

    @Query(value = "SELECT * FROM shipments WHERE id = :id", nativeQuery = true)
    Optional<Shipment> getShipmentById(@Param("id") String id);

    @Modifying
    @Query(value = "INSERT INTO shipments (id, name, fee) VALUES (:id, :name, :fee)", nativeQuery = true)
    int createShipment(@Param("id") String id, @Param("name") String name, @Param("fee") Long fee);

    @Modifying
    @Query(value = "UPDATE shipments SET name = :name, fee = :fee WHERE id = :id", nativeQuery = true)
    int updateArtistById(@Param("id") String id, @Param("name") String name, @Param("fee") Long fee);

    @Modifying
    @Query(value = "DELETE FROM shipments WHERE id = :id", nativeQuery = true)
    void deleteArtistById(@Param("id") String id);
}
