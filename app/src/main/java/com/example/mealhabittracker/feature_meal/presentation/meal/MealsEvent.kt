package com.example.mealhabittracker.feature_meal.presentation.meal

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.util.MealOrder

sealed class MealsEvent {
    data class Order(val mealOrder: MealOrder): MealsEvent()
    data class DeleteMeal(val meal: Meal): MealsEvent()
    object RestoreMeal: MealsEvent()
    object ToggleOrderSection: MealsEvent()
    object NetworkConnectivity: MealsEvent()
}
