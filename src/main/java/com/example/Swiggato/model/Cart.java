package com.example.Swiggato.model;

import jakarta.persistence.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id // this annotation is used to define the primary key for specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation is used to auto generate the value identically
    int id;

    double cardTotal;

    /**
     * cardinal relationship
     * each cart is assigned to each customer so this will be one to one mapping
     * here customer is the parent and cart is child
     */
    @OneToOne
    @JoinColumn // this annotation is used to join primary key of parent class as foreign key for child table
    Customer customer;

    /**
     * each cart can hold multiple food items so this will be one-to-many relationship
     * here cart is the parent for menuItems entities
     */
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    List<FoodItem> foodItems = new ArrayList<>();
}
