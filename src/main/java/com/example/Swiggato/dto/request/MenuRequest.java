package com.example.Swiggato.dto.request;

import com.example.Swiggato.Enum.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequest {
    int restaurantId;

    String dishName;

    double price;

    FoodCategory foodCategory;

    boolean veg;

    boolean available;

}
