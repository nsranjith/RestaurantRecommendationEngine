package com.example.restaurantrecommendation.Service;

import com.example.restaurantrecommendation.model.CostTracking;
import com.example.restaurantrecommendation.model.Cuisine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class CusinePriority {
    private List<Cuisine> primaryCusine;
    private List<Cuisine> secondaryCusines;
    private List<Integer> primaryCostBracket;
    private List<Integer> secondaryCostBrackets;
}
