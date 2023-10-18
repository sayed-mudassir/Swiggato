package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.request.FoodRequest;
import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.dto.response.CartStausResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.exceptions.MenuItemNotFoundException;
import com.example.Swiggato.exceptions.RestaurantNotFoundException;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.model.FoodItem;
import com.example.Swiggato.model.MenuItem;
import com.example.Swiggato.repository.CartRepository;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.FoodRepository;
import com.example.Swiggato.repository.MenuRepository;
import com.example.Swiggato.service.CartService;
import com.example.Swiggato.transformer.CartTransformer;
import com.example.Swiggato.transformer.FoodItemTransformer;
import com.example.Swiggato.utils.validationUtils;
import org.springframework.stereotype.Service;

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
//        prepare food Item
        FoodItem foodItem = FoodItemTransformer.prepareFoodItem(menuItem,foodRequest);
//        get cart
        Cart cart = customer.getCart();
//        save the food item
        FoodItem savedFoodItem = foodRepository.save(foodItem);
//        calculate the cart total
        double cartTotal = 0;
        //        add food item to cart
        cart.getFoodItems().add(savedFoodItem);
        for(FoodItem foodItem1 : cart.getFoodItems())
            cartTotal += foodItem1.getRequiredQuantity()*foodItem1.getMenuItem().getPrice();
        savedFoodItem.setCart(cart);//set cart for food item
        cart.setCardTotal(cartTotal);//set card total for cart
        menuItem.getFoodItems().add(savedFoodItem);// save food item for menuItem
//        save menu and cart
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);
//        prepare cart response return response
        return CartTransformer.cartToCartStatusResponse(savedCart);

    }
}
