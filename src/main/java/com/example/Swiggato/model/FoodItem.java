package com.example.Swiggato.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
@Table(name = "food_item")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int requiredQuantity; // quantity of food required by the customer

    double totalCost; // total cost customer has to pay
    /**
     * each customer's cart can have multiple food item
     * so this will be many-to-one relationship
     * here cart is the parent and food item is the child
     */
    @ManyToOne
    @JoinColumn
    Cart cart;
    /**
     * each restaurant's menu can have multiple food item
     * so this will be many-to-one relationship
     * here menu is the parent and food item is the child
     */
    @ManyToOne
    @JoinColumn
    MenuItem menuItem;
    /**
     * each order can have multiple food item
     * so this will be many-to-one relationship
     * here order is the parent and food item is the child
     */
    @ManyToOne
    @JoinColumn
    OrderEntity order;

}
