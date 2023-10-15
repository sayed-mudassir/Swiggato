package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantTransformer {
    public static Restaurant restaurantRequestToRestaurant(RestaurantRequest restaurantRequest){
        return Restaurant.builder()
                .name(restaurantRequest.getName())
                .location(restaurantRequest.getLocation())
                .contactNo(restaurantRequest.getContactNo())
                .restaurantCategory(restaurantRequest.getRestaurantCategory())
                .opened(true)
                .orders(new ArrayList<>())
                .availableMenuItems(new ArrayList<>())
                .build();
    }
    public static RestaurantResponse restaurantToRestaurantResponse(Restaurant restaurant){
        List<MenuResponse>menu = restaurant.getAvailableMenuItems()
                .stream()
                .map(menuItem -> MenuItemTransformer.menuItemToMenuResponse(menuItem))
                .collect(Collectors.toList());
        return RestaurantResponse.builder()
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .contactNum(restaurant.getContactNo())
                .menu(menu)
                .location(restaurant.getLocation())
                .opened(restaurant.isOpened())
                .build();
    }
}
