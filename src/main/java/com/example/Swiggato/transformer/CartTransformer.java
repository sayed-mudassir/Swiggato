package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.model.Cart;

import java.util.ArrayList;

public class CartTransformer {
    public static CartResponse cartToCartResponse(Cart cart){
        return CartResponse.builder()
                .cartTotal(cart.getCardTotal())
                .foodItems(new ArrayList<>())
                .build();
    }
}
