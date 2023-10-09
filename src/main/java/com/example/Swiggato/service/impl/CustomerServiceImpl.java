package com.example.Swiggato.service.impl;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.exceptions.CustomerNotFoundException;
import com.example.Swiggato.model.Cart;
import com.example.Swiggato.model.Customer;
import com.example.Swiggato.repository.CustomerRepository;
import com.example.Swiggato.service.CustomerService;

import com.example.Swiggato.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
/**
* @Autowired
* CustomerRepository customerRepository;
* this is a field injection
* we need to avoid this because it will create problem in testing
* when mockito pass dummy bean it can throw null pointer exception
 */
    final CustomerRepository customerRepository;
    /*
    * instead of field injection i am using constructor injection
    * by making the bean as final and passing bean into the constructor
    * and instead of directly auto-wiring bean I am auto-wiring this injected bean constructor
    */
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
//        create customer
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);
//        assign cart to customer
        Cart cart = Cart.builder()
                .cardTotal(0)
                .customer(customer)
                .build();
        customer.setCart(cart);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    @Override
    public CustomerResponse findCustomerByMobile(String mobileNo) {
        Customer customer = customerRepository.findByMobileNo(mobileNo);
        if (customer.equals(null)) throw new CustomerNotFoundException("invalid Mobile Number");
        return CustomerTransformer.customerToCustomerResponse(customer);
    }
}
