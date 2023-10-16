package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.dto.response.CartStausResponse;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartTransformer {
    public static CartResponse cartToCartResponse(Cart cart){
        List<FoodResponse> foodResponses = cart.getFoodItems()
                .stream()
                .map(foodItem -> FoodItemTransformer.foodItemToFoodResponse(foodItem))
                .collect(Collectors.toList());
        return CartResponse.builder()
                .cartTotal(cart.getCardTotal())
                .foodItems(foodResponses)
                .build();
    }
    public static CartStausResponse cartToCartStatusResponse(Cart cart){
        List<FoodResponse> foodResponses = cart.getFoodItems()
                .stream()
                .map(foodItem -> FoodItemTransformer.foodItemToFoodResponse(foodItem))
                .collect(Collectors.toList());
        return CartStausResponse.builder()
                .customerName(cart.getCustomer().getName())
                .CustomerAddress(cart.getCustomer().getAddress())
                .CustomerMobileNo(cart.getCustomer().getMobileNo())
                .foodResponses(foodResponses)
                .cartTotal(cart.getCardTotal())
                .build();
    }
}
