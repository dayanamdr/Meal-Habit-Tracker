package com.example.mealhabittracker.feature_meal.domain.use_case

data class MealUseCases(
    val getMeal: GetMeal,
    val getMeals: GetMeals,
    val addMeal : AddMeal,
    val deleteMeal: DeleteMeal,
)
