package com.example.Swiggato.model;

import com.example.Swiggato.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
/*
 * @Data annotation hold the properties of :
 * @Getters, @Setters and @RequiredArgsConstructor
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer")
public class Customer {
    @Id // this annotation is used to define the primary key for specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation is used to auto generate the value identically
    int id;

//    @Size annotation is used to restrict the min or max limit
    @Size(min = 2, message = "{validation.name.size.too_short}")
    @Size(max = 40,message = "{validation.name.size.too_long}")
    String name;

    @Email
    @Column(unique = true,nullable = false) // @Column is used to restrict the column properties
    String email;

    @Column(nullable = false)
    String address;

    @Column(unique = true,nullable = false)
    @Size(min = 10,max = 10) //this is how we can restrict the size
    String mobileNo;

    @Enumerated(EnumType.STRING)
    Gender gender;
    /**
     * cardinal relationship
     * each customer can have only one cart so this will be one-to-one relationship
     * customer is the parent for cart entity
     */
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    Cart cart; // cart is assigned to a customer

    /**
     * one customer can have multiple orders so this will be one-to-many relationship
     * here also customer is the parent for order entities
     */
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<OrderEntity> orderEntities = new ArrayList<>();

}
