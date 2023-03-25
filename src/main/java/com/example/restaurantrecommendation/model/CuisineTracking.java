package com.example.restaurantrecommendation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CuisineTracking {
    private Cuisine type;
    private int noOfOrders;
}
