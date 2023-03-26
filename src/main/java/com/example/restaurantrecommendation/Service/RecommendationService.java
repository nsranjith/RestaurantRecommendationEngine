package com.example.restaurantrecommendation.Service;

import com.example.restaurantrecommendation.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public interface RecommendationService {
    TreeMap<Integer, List<CuisineTracking>> getUsersCusineByNumberOfOrders(User user);
    TreeMap<Integer, List<CostTracking>> getUsersCostBracketByNumberOfOrders(User user);
    List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(List<Restaurant> restaurant, List<Cuisine> cuisine, List<Integer> costBracket, Optional<Float> rating);
    List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingLesserThanOrEqualsTo(List<Restaurant> restaurant, List<Cuisine> cuisine, List<Integer> costBracket, Optional<Float> rating);
    List<String> getTopNewRestaurantsByRating(List<Restaurant> restaurants, int top);
}
