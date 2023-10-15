package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.model.MenuItem;

public class MenuItemTransformer {
    public static MenuResponse menuItemToMenuResponse( MenuItem menuItem){
        return MenuResponse.builder()
                .dishName(menuItem.getDishName())
                .foodCategory(menuItem.getFoodCategory())
                .price(menuItem.getPrice())
                .veg(menuItem.isVeg())
                .build();
    }
    public static MenuItem menuRequestToMenuItem(MenuRequest menuRequest){
        return MenuItem.builder()
                .dishName(menuRequest.getDishName())
                .foodCategory(menuRequest.getFoodCategory())
                .available(menuRequest.isAvailable())
                .price(menuRequest.getPrice())
                .veg(menuRequest.isVeg())
                .build();
    }

}
