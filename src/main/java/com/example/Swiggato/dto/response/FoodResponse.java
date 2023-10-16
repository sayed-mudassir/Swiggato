package com.example.Swiggato.dto.response;

import com.example.Swiggato.Enum.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {
    String dishName;

    double price;

    FoodCategory category;

    boolean veg;

    int quantityAdded;

    String restaurantName;
}
