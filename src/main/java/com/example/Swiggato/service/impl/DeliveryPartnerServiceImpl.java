package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.model.DeliveryPartner;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.service.DeliveryPartnerService;
import com.example.Swiggato.transformer.DeliveryPartnerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {
    final DeliveryPartnerRepository deliveryPartnerRepository;
    @Autowired
    public DeliveryPartnerServiceImpl(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }
    @Override
    public String addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
//        prepare Delivery Partner from dto
        DeliveryPartner deliveryPartner = DeliveryPartnerTransformer.deliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequest);
//        save Delivery Partner to DB
        deliveryPartnerRepository.save(deliveryPartner);
        return "You are successfully registered as a delivery partner!!!!!";
    }
}
