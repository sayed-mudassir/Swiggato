package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.MenuResponse;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.exceptions.RestaurantNotFoundException;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.service.RestaurantService;
import com.example.Swiggato.transformer.MenuItemTransformer;
import com.example.Swiggato.transformer.RestaurantTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Swiggato.utils.validationUtils;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    final RestaurantRepository restaurantRepository;
    final validationUtils validationUtils;
    /**
     * Constructor Injection
     *
     * @param restaurantRepository --> bean of a restaurant repository
     * @param validationUtils
     */
    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 com.example.Swiggato.utils.validationUtils validationUtils) {
        this.restaurantRepository = restaurantRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public RestaurantResponse changeOpenedStatus(int id) {
        if(!validationUtils.validateRestaurantId(id)){
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }
        Restaurant restaurant = restaurantRepository.findById(id).get();
        restaurant.setOpened(!restaurant.isOpened());
        Restaurant savedrestaurant = restaurantRepository.save(restaurant);
        return RestaurantTransformer.restaurantToRestaurantResponse(savedrestaurant);
    }

    @Override
    public RestaurantResponse addRestaurant( RestaurantRequest restaurantRequest){
//        prepare restaurant model from dto
        Restaurant restaurant = RestaurantTransformer.restaurantRequestToRestaurant(restaurantRequest);
//        save restaurant
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
//        model -->> dto
        return RestaurantTransformer.restaurantToRestaurantResponse(savedRestaurant);

    }

    @Override
    public RestaurantResponse addMenuItemToRestaurant(MenuRequest menuRequest) {
//        validate restaurant
        if (!validationUtils.validateRestaurantId(menuRequest.getRestaurantId())) {
            throw new RestaurantNotFoundException("Restaurant doesn't exist!!");
        }
//        getting the restaurant
        Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId()).get();
//        prepare menu from dto request
        MenuItem menuItem = MenuItemTransformer.menuRequestToMenuItem(menuRequest);
        menuItem.setRestaurant(restaurant);

//        add food to restaurant menu item
        restaurant.getAvailableMenuItems().add(menuItem);
//        save restaurant
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
//         prepare response dto
        return RestaurantTransformer.restaurantToRestaurantResponse(savedRestaurant);

    }

    @Override
    public List<MenuResponse> getMenuOfRestaurant(int id) {
        if(!validationUtils.validateRestaurantId(id)){
            throw new RestaurantNotFoundException("Restaurant doesn't exists");
        }
        Restaurant restaurant = restaurantRepository.findById(id).get();
        return RestaurantTransformer.getMenuOfRestaurant(restaurant);
    }
}
