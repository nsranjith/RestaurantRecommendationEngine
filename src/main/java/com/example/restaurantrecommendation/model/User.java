package com.example.restaurantrecommendation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class User {
    private List<CuisineTracking> cuisines;
    private List<CostTracking> costBracket;
}
