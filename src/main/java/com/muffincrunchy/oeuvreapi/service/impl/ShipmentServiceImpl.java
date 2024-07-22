package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.dto.request.CreateShipmentRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateShipmentRequest;
import com.muffincrunchy.oeuvreapi.model.entity.Shipment;
import com.muffincrunchy.oeuvreapi.repository.ShipmentRepository;
import com.muffincrunchy.oeuvreapi.service.ShipmentService;
import com.muffincrunchy.oeuvreapi.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final Validation validation;

    @Override
    public List<Shipment> getAll() {
        return shipmentRepository.getAllShipments();
    }

    @Override
    public Shipment getById(String id) {
        Optional<Shipment> shipment = shipmentRepository.getShipmentById(id);
        return shipment.orElse(null);
    }

    @Override
    public Shipment create(CreateShipmentRequest request) {
        validation.validate(request);
        Shipment shipment = Shipment.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .fee(request.getFee())
                .build();
        int res = shipmentRepository.createShipment(
                shipment.getId(),
                shipment.getName(),
                shipment.getFee()
        );
        if (res == 1) {
            return shipment;
        }
        return null;
    }

    @Override
    public Shipment update(UpdateShipmentRequest request) {
        validation.validate(request);
        int res = shipmentRepository.updateArtistById(
                request.getId(),
                request.getName(),
                request.getFee()
        );
        if (res == 1) {
            return getById(request.getId());
        }
        return null;
    }

    @Override
    public void delete(String id) {
        shipmentRepository.deleteArtistById(id);
    }
}
