package com.example.Swiggato.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
/*
 * @Data annotation hold the properties of :
 * @Getters, @Setters and @RequiredArgsConstructor
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_entity")
public class OrderEntity {
    @Id // this annotation is used to define the primary key for specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation is used to auto generate the value identically
    int id;

    String orderId; //UUID

    double orderTotal;

    @CreationTimestamp
    Date orderTime;

    /**
     * each customer can have multiple order
     * so this will be many-to-one relation
     * here customer is the parent and order entity is the child
     */
    @ManyToOne
    @JoinColumn
    Customer customer;

    /**
     * each delivery can have multiple order
     * so the relationship will be the same
     */
    @ManyToOne
    @JoinColumn
    DeliveryPartner deliveryPartner;

    /**
     * each restaurant can have multiple order
     * so the relationship will be the same
     */
    @ManyToOne
    @JoinColumn
    Restaurant restaurant;

    /**
     * each order con holds multiple food items
     * so this will be one-to-one relationship
     * here order is the parent and food items is the child
     */
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    List<FoodItem> foodItems = new ArrayList<>();


}
