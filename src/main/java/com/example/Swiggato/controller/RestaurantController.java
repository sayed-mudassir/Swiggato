package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.MenuRequest;
import com.example.Swiggato.dto.request.RestaurantRequest;
import com.example.Swiggato.dto.response.RestaurantResponse;
import com.example.Swiggato.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    final RestaurantService restaurantService;
    /**
     * Constructor Injection
     * @param restaurantService --> bean of a restaurant  Service
     */
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        return new ResponseEntity(restaurantService.addRestaurant(restaurantRequest), HttpStatus.CREATED);
    }
    @PutMapping("/update/status")
    public ResponseEntity changeOpenedStatus(@RequestParam("id") int id){
        try {
            RestaurantResponse restaurantResponse = restaurantService.changeOpenedStatus(id);
            return new ResponseEntity(restaurantResponse,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-food")
    public  ResponseEntity addMenuItemToRestaurant(MenuRequest menuRequest){
        try {
            RestaurantResponse restaurantResponse = restaurantService.addMenuItemToRestaurant(menuRequest);
            return new ResponseEntity(restaurantResponse,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
