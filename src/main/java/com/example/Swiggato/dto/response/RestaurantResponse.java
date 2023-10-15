package com.example.Swiggato.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
    String name;

    String location;

    String contactNum;

    List<MenuResponse> menu;

    boolean opened;
}
