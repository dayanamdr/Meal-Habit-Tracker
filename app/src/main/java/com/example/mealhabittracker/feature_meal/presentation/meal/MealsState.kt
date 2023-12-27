package com.example.mealhabittracker.feature_meal.presentation.meal

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.util.MealOrder
import com.example.mealhabittracker.feature_meal.domain.util.OrderType

data class MealsState(
    val meals: List<Meal> = emptyList(),
    val mealOrder: MealOrder = MealOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
