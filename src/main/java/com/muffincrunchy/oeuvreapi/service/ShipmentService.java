package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateShipmentRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateShipmentRequest;
import com.muffincrunchy.oeuvreapi.model.entity.Shipment;

import java.util.List;

public interface ShipmentService {

    List<Shipment> getAll();
    Shipment getById(String id);
    Shipment create(CreateShipmentRequest request);
    Shipment update(UpdateShipmentRequest request);
    void delete(String id);
}
