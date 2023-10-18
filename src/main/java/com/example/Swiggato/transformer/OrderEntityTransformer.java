package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.OrderEntity;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
public class OrderEntityTransformer {
    public static OrderEntity prepareOrderEntity(Cart cart){
        return OrderEntity.builder()
                .orderId(String.valueOf(UUID.randomUUID()))
                .orderTotal(cart.getCardTotal())
                .build();
    }
    public static OrderResponse orderEntityToOrderResponse(OrderEntity orderEntity){
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for (FoodItem foodItem : orderEntity.getFoodItems()){
            foodResponseList.add(FoodItemTransformer.foodItemToFoodResponse(foodItem));
        }
        return OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .orderTime(orderEntity.getOrderTime())
                .orderTotal(orderEntity.getOrderTotal())
                .restaurantName(orderEntity.getRestaurant().getName())
                .customerMobile(orderEntity.getCustomer().getMobileNo())
                .customerName(orderEntity.getCustomer().getName())
                .deliveryPartnerMobile(orderEntity.getDeliveryPartner().getMobileNo())
                .deliveryPartnerName(orderEntity.getDeliveryPartner().getName())
                .foodItems(foodResponseList)
                .build();
    }
}
