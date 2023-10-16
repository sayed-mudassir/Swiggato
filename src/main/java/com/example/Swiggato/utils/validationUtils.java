package com.example.Swiggato.utils;

import com.example.Swiggato.model.Customer;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.model.Restaurant;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class validationUtils {
    final RestaurantRepository restaurantRepository;

    final CustomerRepository customerRepository;

    final MenuRepository menuRepository;


    @Autowired
    public validationUtils(RestaurantRepository restaurantRepository,
                           CustomerRepository customerRepository,
                           MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
    }

    public boolean validateRestaurantId(int id){
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        return restaurantOptional.isPresent();
    }
    public  boolean validateCustomerMobile(String mobileNo){
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findByMobileNo(mobileNo));
        return customerOptional.isPresent();
    }
    public  boolean validateMneuItem(int id){
        Optional<MenuItem> menuItemOptional = menuRepository.findById(id);
        return menuItemOptional.isPresent();
    }
}
