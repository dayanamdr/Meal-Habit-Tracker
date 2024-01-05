package com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository

class DeleteMeal(
    private val repository: MealRepository
) {

    suspend operator fun invoke(meal: Meal) {
        repository.deleteMeal(meal)
    }
}