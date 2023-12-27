package com.example.mealhabittracker.feature_meal.presentation.util

sealed class Screen(val route: String) {
    object MealsScreen: Screen("meals_screen")
    object AddMealScreen: Screen("add_meal_screen")
}
