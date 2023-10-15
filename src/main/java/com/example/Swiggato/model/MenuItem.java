package com.example.Swiggato.model;

import com.example.Swiggato.Enum.FoodCategory;
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
@Table(name = "menu_item")
public class MenuItem {
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
     * each restaurant can hold multiple food items
     * so the relationship will be same
     */
    @ManyToOne
    @JoinColumn //this annotation is used to join a foreign key column which holds the primary key of parent class
    Restaurant restaurant;
    /**
     * each restaurant's menu can have multiple food item
     * so this will be many-to-one relationship
     * here menu is the parent and food item is the child
     */
    @OneToMany(mappedBy = "menuItem",cascade = CascadeType.ALL)
    List<FoodItem>foodItems = new ArrayList<>();
}
