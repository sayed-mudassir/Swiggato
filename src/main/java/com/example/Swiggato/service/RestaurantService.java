package com.example.Swiggato.service;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;

public interface RestaurantService {
    RestaurantResponse changeOpenedStatus(int id);
    RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse addMenuItemToRestaurant(MenuRequest menuRequest);
}
