package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.FoodResponse;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;

public class FoodItemTransformer {
    public static FoodItem prepareFoodItem(MenuItem menuItem , FoodRequest foodRequest){
        return FoodItem.builder()
                .menuItem(menuItem)
                .requiredQuantity(foodRequest.getRequiredQuantity())
                .totalCost(foodRequest.getRequiredQuantity()* menuItem.getPrice())
                .build();
    }
    public static FoodResponse foodItemToFoodResponse(FoodItem foodItem){
        return FoodResponse.builder()
                .dishName(foodItem.getMenuItem().getDishName())
                .category(foodItem.getMenuItem().getFoodCategory())
                .price(foodItem.getMenuItem().getPrice())
                .veg(foodItem.getMenuItem().isVeg())
                .quantityAdded(foodItem.getRequiredQuantity())
                .restaurantName(foodItem.getMenuItem().getRestaurant().getName())
                .build();
    }
}
