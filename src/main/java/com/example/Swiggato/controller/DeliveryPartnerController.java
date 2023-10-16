package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.DeliveryPartnerRequest;
import com.example.Swiggato.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Delivery-Partner")
public class DeliveryPartnerController {
    final DeliveryPartnerService deliveryPartnerService;
    @Autowired
    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }
    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest){
        try{
            String deliveryPartnerResponse = deliveryPartnerService.addDeliveryPartner(deliveryPartnerRequest);
            return new ResponseEntity(deliveryPartnerResponse, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
