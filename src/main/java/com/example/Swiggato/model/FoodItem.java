package com.example.Swiggato.model;

import com.example.Swiggato.Enum.FoodCategory;
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
    @Id // this annotation is used to define the primary key for specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation is used to auto generate the value identically
    int id;

    String dishName;

    double price;

    @Enumerated(EnumType.STRING) // this annotation is used to represent the enum value as string in database
    FoodCategory foodCategory;

    boolean veg;

    boolean available;
    /**
     * each cart can hold multiple food items
     * so this will hold many-to-one relationship
     * where cart is the parent and food items is the child
     */
    @ManyToOne
    @JoinColumn //this annotation is used to join a foreign key column which holds the primary key of parent class
    Cart cart;
    /**
     * each order can hold multiple food items
     * so the relationship will be same
     */
    @ManyToOne
    @JoinColumn //this annotation is used to join a foreign key column which holds the primary key of parent class
    OrderEntity order;
    /**
     * each restaurant can hold multiple food items
     * so the relationship will be same
     */
    @ManyToOne
    @JoinColumn //this annotation is used to join a foreign key column which holds the primary key of parent class
    Restaurant restaurant;

}
