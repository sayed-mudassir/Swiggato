package com.example.Swiggato.model;

import com.example.Swiggato.Enum.Gender;
import jakarta.persistence.*;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "delivery_partner")
public class DeliveryPartner {
    @Id // this annotation is used to define the primary key for specific table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation is used to auto generate the value identically
    int id;

    String name;

    @Size(min = 10,max = 10)
    String mobileNo;

    @Enumerated(EnumType.STRING)
    Gender gender;

    /**
     * each delivery partner can hold multiple orders
     * so this will be one-to-many relationship
     * here delivery partner is the parent and order entity is the child
     */
    @OneToMany(mappedBy = "deliveryPartner",cascade = CascadeType.ALL)
    List<OrderEntity> order = new ArrayList<>();


}
