package com.example.Swiggato.model;

import com.example.Swiggato.Enum.RestaurantCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
/*
 * @Data annotation hold the properties of :
 * @Getters, @Setters and @RequiredArgsConstructor
 */
@Builder
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id // this annotation is used to define the primary key for specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation is used to auto generate the value identically
    int id;

    String name;

    String location;

    boolean opened;

    @Column(unique = true,nullable = false)
    @Size(min = 10,max = 10) //@Size annotation is used to restrict the min or max limit
    String contactNo;

    @Enumerated(EnumType.STRING)
    RestaurantCategory restaurantCategory;

    /**
     * each restaurant cah holds multiple orders
     * so the relationship will be one-to-many
     * here restaurant is the parent and order will be the child
     */
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    List<OrderEntity> orders = new ArrayList<>();

    /**
     * each restaurant cah holds multiple food items
     * so the relationship will be same
     */
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    List<MenuItem> availableMenuItems = new ArrayList<>();

}
