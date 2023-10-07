package com.example.Swiggato.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int cardTotal;

    /**   cardinal relationship
     * each cart is assigned to each customer so this will be one to one mapping
     * here customer is the parent and cart is child
     */
    @OneToOne
    @JoinColumn // this annotation is used to join primary key of parent class as foreign key for child table
    Customer customer;

    /**   each cart can hold multiple food items so this will be one to many relationship
     *    here cart is the parent for foodItems entities
     */
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    List<FoodItem> foodItems = new ArrayList<>();
}
