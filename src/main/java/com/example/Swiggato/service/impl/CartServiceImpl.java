package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.dto.response.CartStausResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.exceptions.MenuItemNotFoundException;
import com.example.Swiggato.exceptions.RestaurantNotFoundException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodRepository;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.service.CartService;
import com.example.Swiggato.transformer.CartTransformer;
import com.example.Swiggato.transformer.FoodItemTransformer;
import com.example.Swiggato.utils.validationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    final MenuRepository menuRepository;
    final CustomerRepository customerRepository;
    final CartRepository cartRepository;
    final FoodRepository foodRepository;
    final validationUtils validationUtils;


    public CartServiceImpl(MenuRepository menuRepository,
                           CustomerRepository customerRepository,
                           CartRepository cartRepository,
                           FoodRepository foodRepository,
                           validationUtils validationUtils) {
        this.menuRepository = menuRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.foodRepository = foodRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public CartStausResponse addFoodItemToCart(FoodRequest foodRequest) {
//        validate customer
        if(!validationUtils.validateCustomerMobile(foodRequest.getCustomerMobileNo()))
            throw new CustomerNotFoundException("Customer doesn't exists");
//          get customer
        Customer customer = customerRepository.findByMobileNo(foodRequest.getCustomerMobileNo());
//        validate Menu Item
        if(!validationUtils.validateMneuItem(foodRequest.getMenuItemId()))
            throw new MenuItemNotFoundException("Item not available in this restaurant !!!!!");
        MenuItem menuItem = menuRepository.findById(foodRequest.getMenuItemId()).get();
//        check if restaurant is opened
        if(!menuItem.getRestaurant().isOpened())
            throw new RestaurantNotFoundException("Restaurant is currently closed!!!!!");
//        check weather item is available is present
        if (!menuItem.isAvailable())
            throw new MenuItemNotFoundException("sorry currently Item is out of stock !!!!!");

//        get cart
        Cart cart = customer.getCart();
        if(cart.getFoodItems().size()!=0){
            Restaurant currRestaurant = cart
                    .getFoodItems()
                    .get(0)
                    .getMenuItem()
                    .getRestaurant();
            Restaurant newRestaurant = menuItem.getRestaurant();
            /**
             * if the new item added to cart holds different restaurant then we need to clear the previous items from cart
             * and assign the new item to the cart
             */
            if(!currRestaurant.equals(newRestaurant)){
                List<FoodItem>foodItems = cart.getFoodItems();
                for(FoodItem foodItem : foodItems){
                    foodItem.setCart(null);
                    foodItem.setOrder(null);
                    foodItem.setMenuItem(null);
                }
                foodRepository.deleteAll(foodItems);
                cart.setCardTotal(0);
                cart.getFoodItems().clear();
            }

        }
//        check if the item is already exists in cart or not
        boolean alreadyExists = false;
        FoodItem savedFoodItem = null;
//      if food item already exists then update the current value of food item
        if(cart.getFoodItems().size()!=0){
            for(FoodItem foodItem : cart.getFoodItems()){
                if(foodItem.getMenuItem().getId() == menuItem.getId()){
                    savedFoodItem = foodItem;
                    int curr = foodItem.getRequiredQuantity();
                    foodItem.setRequiredQuantity(curr+foodRequest.getRequiredQuantity());
                    alreadyExists = true;
                    break;
                }
            }
        }
//      if food item isn't exists in cart add new item to cart
        if(!alreadyExists){
            //        prepare food Item
            FoodItem foodItem = FoodItemTransformer.prepareFoodItem(menuItem,foodRequest);
            //        save the food item
            savedFoodItem = foodRepository.save(foodItem);
            //        add food item to cart
            cart.getFoodItems().add(savedFoodItem);
            menuItem.getFoodItems().add(savedFoodItem);// save food item for menuItem
            savedFoodItem.setCart(cart);//set cart for food item
        }

//        calculate the cart total
        double cartTotal = 0;

        for(FoodItem foodItem1 : cart.getFoodItems())
            cartTotal += foodItem1.getRequiredQuantity()*foodItem1.getMenuItem().getPrice();
        cart.setCardTotal(cartTotal);//set card total for cart

//        save menu and cart
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);
//        prepare cart response return response
        return CartTransformer.cartToCartStatusResponse(savedCart);

    }
}
