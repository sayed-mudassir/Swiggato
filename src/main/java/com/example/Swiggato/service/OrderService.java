package com.example.Swiggato.service;

import com.example.Swiggato.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(String customerMobileNo);
}
