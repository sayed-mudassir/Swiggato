package com.example.Swiggato.dto.request;

import com.example.Swiggato.Enum.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {
    String name;

    String location;

    RestaurantCategory restaurantCategory;

    String contactNo;
}
