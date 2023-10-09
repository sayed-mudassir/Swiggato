package com.example.Swiggato.controller;

import com.example.Swiggato.dto.request.CustomerRequest;
import com.example.Swiggato.dto.response.CustomerResponse;
import com.example.Swiggato.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    /*
     * @Autowired
     * CustomerService customerService;
     * this is a field injection
     * we need to avoid this because it will create problem in testing
     * when mockito pass dummy bean it can throw null pointer exception
     */
    final CustomerServiceImpl customerService;

    /*
     * instead of field injection i am using constructor injection
     * by making the bean as final and passing bean into the constructor
     * and instead of directly auto-wiring bean I am auto-wiring this injected bean constructor
     */
    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        return new ResponseEntity(customerService.addCustomer(customerRequest), HttpStatus.CREATED);
    }
    @GetMapping("/find/mobile/{mobile}")
    public ResponseEntity findCustomerByMobile(@PathVariable("mobile") String mobileNo){
        try {
            CustomerResponse customerResponse = customerService.findCustomerByMobile(mobileNo);
            return new ResponseEntity(customerResponse,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
