package com.example.restaurantrecommendation.Service;

import com.example.restaurantrecommendation.model.*;

import java.util.List;
import java.util.Map;

public interface RecommendationService {
    Map<Integer, List<CuisineTracking>> getUsersCusineByNumberOfOrders(User user);
    Map<Integer, List<CostTracking>> getUsersCostBracketByNumberOfOrders(User user);
    List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(List<Restaurant> restaurant, Cuisine cuisine, int costBracket, int rating);
    List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingLesserThanOrEqualsTo(List<Restaurant> restaurant, Cuisine cuisine, int costBracket, int rating);
    List<String> getTopNewRestaurantsByRating(List<Restaurant>, int top, int rating);
}
