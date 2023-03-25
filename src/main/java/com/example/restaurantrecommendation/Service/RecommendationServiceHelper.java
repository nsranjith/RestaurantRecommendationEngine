package com.example.restaurantrecommendation.Service;

import com.example.restaurantrecommendation.model.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Slf4j
public class RecommendationServiceHelper implements RecommendationService {

    @Override
    public Map<Integer, List<CuisineTracking>> getUsersCusineByNumberOfOrders(User user) {
        List<CuisineTracking> cuisineTrackingList=user.getCuisines();
        return cuisineTrackingList.stream().collect(groupingBy(CuisineTracking::getNoOfOrders));
    }

    @Override
    public Map<Integer, List<CostTracking>> getUsersCostBracketByNumberOfOrders(User user) {
        List<CostTracking> costTrackingList=user.getCostBracket();
        return costTrackingList.stream().collect(groupingBy(CostTracking::getNoOfOrders));
    }

    @Override
    public List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(List<Restaurant> restaurants, Cuisine cuisine, int costBracket, int rating) {
        return restaurants.stream().filter(restaurant -> restaurant.getCuisine().toString().equalsIgnoreCase(cuisine.toString()))
                .filter(restaurant -> restaurant.getCostBracket()>=costBracket).map(Restaurant::getRestaurantId).collect(Collectors.toList());
    }

    @Override
    public List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingLesserThanOrEqualsTo(List<Restaurant> restaurants, Cuisine cuisine, int costBracket, int rating) {
        return restaurants.stream().filter(restaurant -> restaurant.getCuisine().toString().equalsIgnoreCase(cuisine.toString()))
                .filter(restaurant -> restaurant.getCostBracket()<costBracket).map(Restaurant::getRestaurantId).collect(Collectors.toList());
    }

    @Override
    public List<String> getTopNewRestaurantsByRating(List<Restaurant> restaurants, int top, int rating) {
        return null;
    }
}
