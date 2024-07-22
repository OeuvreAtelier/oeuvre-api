package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.ItemCategory;
import com.muffincrunchy.oeuvreapi.model.constant.ItemType;
import com.muffincrunchy.oeuvreapi.model.constant.PaymentType;
import com.muffincrunchy.oeuvreapi.model.dto.request.CreateMerchRequest;
import com.muffincrunchy.oeuvreapi.model.dto.request.UpdateMerchRequest;
import com.muffincrunchy.oeuvreapi.model.entity.Merch;
import com.muffincrunchy.oeuvreapi.model.entity.Payment;
import com.muffincrunchy.oeuvreapi.model.entity.Shipment;
import com.muffincrunchy.oeuvreapi.repository.MerchRepository;
import com.muffincrunchy.oeuvreapi.service.*;
import com.muffincrunchy.oeuvreapi.utils.Validation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MerchServiceImpl implements MerchService {

    private final MerchRepository merchRepository;
    private final ArtistService artistService;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final PaymentService paymentService;
    private final ShipmentService shipmentService;
    private final Validation validation;

    @PostConstruct
    public void init() {
        for (ItemCategory itemCategory : ItemCategory.values()) {
            categoryService.getOrSave(itemCategory);
        }
        for (ItemType itemType : ItemType.values()) {
            typeService.getOrSave(itemType);
        }
        for (PaymentType paymentType : PaymentType.values()) {
            paymentService.getOrSave(paymentType);
        }
    }

    @Override
    public List<Merch> getAll() {
        return merchRepository.getAllMerchs();
    }

    @Override
    public Merch getById(String id) {
        Optional<Merch> merch = merchRepository.getMerchById(id);
        return merch.orElse(null);
    }

    @Override
    public Merch create(CreateMerchRequest request) {
        validation.validate(request);
        Merch merch = Merch.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .category(categoryService.getOrSave(ItemCategory.valueOf(request.getCategory())))
                .price(request.getPrice())
                .stock(request.getStock())
                .artist(artistService.getById(request.getArtistId()))
                .type(typeService.getOrSave(ItemType.valueOf(request.getType())))
                .build();
        int res = merchRepository.createMerch(
                merch.getId(),
                merch.getName(),
                merch.getCategory().getId(),
                merch.getPrice(),
                merch.getStock(),
                merch.getArtist().getId(),
                merch.getType().getId()
        );
        if (res == 1) {
            List<Payment> payments = new ArrayList<>();
            for (Payment payment : paymentService.getAll()) {
                merchRepository.createPayment(
                        merch.getId(),
                        payment.getId()
                );
                payments.add(payment);
            }

            List<Shipment> shipments = new ArrayList<>();
            for (Shipment shipment : shipmentService.getAll()) {
                merchRepository.createShipment(
                        merch.getId(),
                        shipment.getId()
                );
                shipments.add(shipment);
            }

            merch.setPayments(payments);
            merch.setShipments(shipments);
            return merch;
        }
        return null;
    }

    @Override
    public Merch update(UpdateMerchRequest request) {
        validation.validate(request);
        int res = merchRepository.updateMerchById(
                request.getId(),
                request.getName(),
                categoryService.getOrSave(ItemCategory.valueOf(request.getCategory())).getId(),
                request.getPrice(),
                request.getStock(),
                request.getArtistId(),
                typeService.getOrSave(ItemType.valueOf(request.getType())).getId()
        );
        if (res == 1) {
            return getById(request.getId());
        }
        return null;
    }

    @Override
    public void delete(String id) {
        merchRepository.delete(getById(id));
    }
}
