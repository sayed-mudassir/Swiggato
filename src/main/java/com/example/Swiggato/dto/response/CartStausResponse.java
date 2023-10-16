package com.example.Swiggato.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartStausResponse {
    String customerName;

    String CustomerAddress;

    String CustomerMobileNo;

    double cartTotal;

    List<FoodResponse> foodResponses = new ArrayList<>();
}
