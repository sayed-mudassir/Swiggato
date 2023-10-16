package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.dto.response.CartStausResponse;

public interface CartService {
    CartStausResponse addFoodItemToCart(FoodRequest foodRequest);
}
