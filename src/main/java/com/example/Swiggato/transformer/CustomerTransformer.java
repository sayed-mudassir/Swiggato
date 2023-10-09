package com.example.Swiggato.transformer;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CartResponse;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.model.Customer;

public class CustomerTransformer {
    public static Customer customerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .gender(customerRequest.getGender())
                .address(customerRequest.getAddress())
                .mobileNo(customerRequest.getMobileNo())
                .email(customerRequest.getEmail())
                .build();
    }
    public static CustomerResponse customerToCustomerResponse(Customer customer){
        CartResponse cartResponse = CartTransformer.cartToCartResponse(customer.getCart());
        return CustomerResponse.builder()
                .name(customer.getName())
                .mobileNo(customer.getMobileNo())
                .address(customer.getAddress())
                .cartResponse(cartResponse)
                .build();
    }
}
