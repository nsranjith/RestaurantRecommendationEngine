package com.example.restaurantrecommendation.Service;

import com.example.restaurantrecommendation.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.restaurantrecommendation.model.Constants.*;

@Slf4j
@Service
public class RecommendationServiceHelper {
    private final RecommendationServiceImpl recommendationServiceImpl;
    private final CusinePriority cusinePriority;

    public RecommendationServiceHelper(RecommendationServiceImpl recommendationServiceImpl, CusinePriority cusinePriority) {
        this.recommendationServiceImpl = recommendationServiceImpl;
        this.cusinePriority = cusinePriority;
    }

    public List<String> getRestaurantRecommendations(User user, List<Restaurant> availableRestaurants) {
        List<List<String>> resultantRestuarantIds=new ArrayList<>();
        TreeMap<Integer, List<CuisineTracking>> cuisineByNoOfOrders= recommendationServiceImpl.getUsersCusineByNumberOfOrders(user);
        TreeMap<Integer, List<CostTracking>> costBracketByNumberOfOrders = recommendationServiceImpl.getUsersCostBracketByNumberOfOrders(user);
        UpadteUsersCuisinePriority(cuisineByNoOfOrders, costBracketByNumberOfOrders);
        return getOrderedList(availableRestaurants,cusinePriority, resultantRestuarantIds).stream().limit(TO_BE_SHOWN_LIMIT).collect(Collectors.toList());
    }

    private List<String> getOrderedList(List<Restaurant> availableRestaurants,CusinePriority cusinePriority, List<List<String>> resultantRestuarantIds) {
        List<String> primaryCusinePrimaryCostList= recommendationServiceImpl.
                getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(availableRestaurants,
                        cusinePriority.getPrimaryCusine(),cusinePriority.getPrimaryCostBracket(), Optional.of(MIN_RATING));
        if(primaryCusinePrimaryCostList.isEmpty()){
            resultantRestuarantIds.add(recommendationServiceImpl.
                    getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(availableRestaurants,
                            cusinePriority.getPrimaryCusine(),cusinePriority.getSecondaryCostBrackets(), Optional.of(MIN_RATING)));
            resultantRestuarantIds.add(recommendationServiceImpl.
                    getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(availableRestaurants,
                            cusinePriority.getSecondaryCusines(),cusinePriority.getPrimaryCostBracket(), Optional.of(MIN_RATING)));
        }else{
                resultantRestuarantIds.add(primaryCusinePrimaryCostList);
        }
        resultantRestuarantIds.add(recommendationServiceImpl.
                getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(availableRestaurants,
                        cusinePriority.getPrimaryCusine(),cusinePriority.getPrimaryCostBracket(), Optional.of(RATING_4)));
        resultantRestuarantIds.add(recommendationServiceImpl.
                getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(availableRestaurants,
                        cusinePriority.getPrimaryCusine(),cusinePriority.getSecondaryCostBrackets(), Optional.of(RATING_4)));
        resultantRestuarantIds.add(recommendationServiceImpl.
                getRestaurantsHavingCuisineWithCostBracketAndRatingGreaterThanOrEqualsTo(availableRestaurants,
                        cusinePriority.getSecondaryCusines(),cusinePriority.getPrimaryCostBracket(), Optional.of(RATING_4_5)));
        resultantRestuarantIds.add(recommendationServiceImpl.getTopNewRestaurantsByRating(availableRestaurants,TOP));
        resultantRestuarantIds.add(recommendationServiceImpl.
                getRestaurantsHavingCuisineWithCostBracketAndRatingLesserThanOrEqualsTo(availableRestaurants,
                        cusinePriority.getPrimaryCusine(),cusinePriority.getPrimaryCostBracket(), Optional.of(RATING_4)));
        resultantRestuarantIds.add(recommendationServiceImpl.
                getRestaurantsHavingCuisineWithCostBracketAndRatingLesserThanOrEqualsTo(availableRestaurants,
                        cusinePriority.getPrimaryCusine(),cusinePriority.getSecondaryCostBrackets(), Optional.of(RATING_4_5)));
        resultantRestuarantIds.add(recommendationServiceImpl.
                getRestaurantsHavingCuisineWithCostBracketAndRatingLesserThanOrEqualsTo(availableRestaurants,
                        cusinePriority.getSecondaryCusines(),cusinePriority.getPrimaryCostBracket(), Optional.of(RATING_4_5)));
        List<String> orderedList=resultantRestuarantIds.stream().flatMap(List :: stream).collect(Collectors.toList());
        availableRestaurants.stream().filter(restaurant->!orderedList.contains(restaurant.getRestaurantId())).
                map(restaurant->orderedList.add(restaurant.getRestaurantId()));
        return orderedList;
    }

    private void UpadteUsersCuisinePriority(TreeMap<Integer, List<CuisineTracking>> cuisineByNoOfOrders, TreeMap<Integer, List<CostTracking>> costBracketByNumberOfOrders) {
        setPrimaryAndSecondaryCuisine(cuisineByNoOfOrders);
        setPrimaryAndSecondaryCost(costBracketByNumberOfOrders);
    }

    private void setPrimaryAndSecondaryCuisine(TreeMap<Integer, List<CuisineTracking>> cuisineByNoOfOrders) {
        boolean primaryAdded=false;
        List<Cuisine> primaryCuisines=new ArrayList<>();
        List<Cuisine> secondaryCuisines=new ArrayList<>();
        for(Map.Entry<Integer, List<CuisineTracking>> cusinesByOrder : cuisineByNoOfOrders.entrySet()){
            if(!primaryAdded){
                cusinesByOrder.getValue().stream().map(cuisineTracking->primaryCuisines.add(cuisineTracking.getType()));
                primaryAdded=true;
            }else{
                cusinesByOrder.getValue().stream().map(cuisineTracking->secondaryCuisines.add(cuisineTracking.getType()));
            }
        }
    }
    private void setPrimaryAndSecondaryCost(TreeMap<Integer, List<CostTracking>> costBracketByNumberOfOrders) {
        boolean primaryAdded=false;
        List<Integer> primaryCostBracket=new ArrayList<>();
        List<Integer> secondaryCostBracket=new ArrayList<>();
        for(Map.Entry<Integer, List<CostTracking>> costBracketByOrder : costBracketByNumberOfOrders.entrySet()){
            if(!primaryAdded){
                costBracketByOrder.getValue().stream().map(costTracking->primaryCostBracket.add(costTracking.getType()));
                primaryAdded=true;
            }else{
                costBracketByOrder.getValue().stream().map(costTracking->secondaryCostBracket.add(costTracking.getType()));
            }
        }
    }
}
