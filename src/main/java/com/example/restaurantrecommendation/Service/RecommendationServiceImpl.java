package com.example.restaurantrecommendation.Service;

import com.example.restaurantrecommendation.model.*;
import com.sun.source.tree.Tree;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.restaurantrecommendation.model.Constants.MAX_RATING;
import static com.example.restaurantrecommendation.model.Constants.MIN_RATING;
import static java.util.stream.Collectors.groupingBy;

@Getter
@Slf4j
@Component
public class RecommendationServiceImpl implements RecommendationService {

    @Override
    public TreeMap<Integer, List<CuisineTracking>> getUsersCusineByNumberOfOrders(User user) {
        List<CuisineTracking> cuisineTrackingList=user.getCuisines();
        Map<Integer, List<CuisineTracking>> groupedMap=cuisineTrackingList.stream().collect(groupingBy(CuisineTracking::getNoOfOrders));
        return new TreeMap<>(groupedMap);
    }

    @Override
    public TreeMap<Integer, List<CostTracking>> getUsersCostBracketByNumberOfOrders(User user) {
        List<CostTracking> costTrackingList=user.getCostBracket();
        return new TreeMap<>(costTrackingList.stream().collect(groupingBy(CostTracking::getNoOfOrders)));
    }

    @Override
    public List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(List<Restaurant> restaurants, List<Cuisine> cuisine, List<Integer> costBracket, Optional<Float> optionalRating) {
        return restaurants.stream().filter(restaurant -> cuisine.contains(restaurant.getCuisine()))
                .filter(restaurant -> costBracket.contains(restaurant.getCostBracket())).map(Restaurant::getRestaurantId).collect(Collectors.toList());
    }

    @Override
    public List<String> getRestaurantsHavingCuisineWithCostBracketAndRatingLesserThanOrEqualsTo(List<Restaurant> restaurants, List<Cuisine> cuisine, List<Integer> costBracket, Optional<Float>  optionalRating) {
        return restaurants.stream().filter(restaurant -> restaurant.getCuisine().toString().equalsIgnoreCase(cuisine.toString()))
                .filter(restaurant -> costBracket.contains(restaurant.getCostBracket())).map(Restaurant::getRestaurantId).collect(Collectors.toList());
    }

    @Override
    public List<String> getTopNewRestaurantsByRating(List<Restaurant> restaurants, int top) {
         return restaurants.stream()
                 .sorted(Comparator.comparing(Restaurant::getRating)
                         .thenComparing(Restaurant::getOnboardedTime).reversed())
                 .limit(top)
                 . map(Restaurant::getRestaurantId)
         .collect(Collectors.toList());

    }
}
