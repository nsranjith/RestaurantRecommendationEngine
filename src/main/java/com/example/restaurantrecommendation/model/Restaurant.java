package com.example.restaurantrecommendation.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class Restaurant {
    private String restaurantId;
    private Cuisine cuisine;
    private int costBracket;
    private float rating;
    private boolean isRecommended;
    private Date onboardedTime;

}
