package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.response.OrderResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.exceptions.EmptyCartException;
import com.example.Swiggato.model.*;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.repository.DeliveryPartnerRepository;
import com.example.Swiggato.repository.OrderRepository;
import com.example.Swiggato.repository.RestaurantRepository;
import com.example.Swiggato.service.OrderService;
import com.example.Swiggato.transformer.OrderEntityTransformer;
import com.example.Swiggato.utils.validationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {
    final validationUtils validationUtils;
    final CustomerRepository customerRepository;
    final OrderRepository orderRepository;
    final DeliveryPartnerRepository deliveryPartnerRepository;
    final RestaurantRepository restaurantRepository;
    final JavaMailSender javaMailSender;
    @Autowired
    public OrderServiceImpl(com.example.Swiggato.utils.validationUtils validationUtils,
                            CustomerRepository customerRepository,
                            OrderRepository orderRepository,
                            DeliveryPartnerRepository deliveryPartnerRepository,
                            RestaurantRepository restaurantRepository,
                            JavaMailSender javaMailSender) {
        this.validationUtils = validationUtils;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.restaurantRepository = restaurantRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public OrderResponse placeOrder(String customerMobileNo) {
//        validate customer mobile number
        if (!validationUtils.validateCustomerMobile(customerMobileNo))
            throw new CustomerNotFoundException("Mobile number is invalid!!!!!!");
//        get customer
        Customer customer = customerRepository.findByMobileNo(customerMobileNo);
//        verify if customer's is empty or not
        Cart cart = customer.getCart();
        if(cart.getFoodItems().size()==0)
            throw new EmptyCartException("Sorry !! you cart is empty");
//        get restaurant if cart is not empty
        Restaurant restaurant = cart
                .getFoodItems()
                .get(0)
                .getMenuItem()
                .getRestaurant();
//        randomly assign delivery partner to deliver the order
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findRandomDeliveryPartner();
//        prepare order Entity
        OrderEntity orderEntity = OrderEntityTransformer.prepareOrderEntity(cart);
//        save orderEntity
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
//        set remaining attributes
        orderEntity.setCustomer(customer);
        orderEntity.setDeliveryPartner(deliveryPartner);
        orderEntity.setRestaurant(restaurant);
        orderEntity.setFoodItems(cart.getFoodItems());
//        handle bidirectional mapping
        customer.getOrderEntities().add(savedOrderEntity);
        deliveryPartner.getOrder().add(savedOrderEntity);
        restaurant.getOrders().add(savedOrderEntity);
//        remove foodItem from cart and set it to Order
        for(FoodItem foodItem : cart.getFoodItems()){
            foodItem.setCart(null);
            foodItem.setOrder(savedOrderEntity);
        }
        clearCart(cart);

        customerRepository.save(customer);
        deliveryPartnerRepository.save(deliveryPartner);
        restaurantRepository.save(restaurant);
//        prepare email content
        String text = "Thank you "+ customer.getName()+" for ordering form Swiggato"+"\n\n"
                +"your order is prepared by "+restaurant.getName()+"\n"
                +"Your order will be delivered quickly to your given address."
                +customer.getAddress()+"\n"
                +"Total amount : " +orderEntity.getOrderTotal()+"\n"
                +"contact your delivery person "+deliveryPartner.getMobileNo();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("spirngaccio@gmail.com");
        simpleMailMessage.setTo(customer.getEmail());
        simpleMailMessage.setSubject("Order Placed !!!");
        simpleMailMessage.setText(text);

//        send mail
        javaMailSender.send(simpleMailMessage);
//        prepare response and return
        return OrderEntityTransformer.orderEntityToOrderResponse(savedOrderEntity);
    }
    private void clearCart(Cart cart) {
        cart.setCardTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }
}
