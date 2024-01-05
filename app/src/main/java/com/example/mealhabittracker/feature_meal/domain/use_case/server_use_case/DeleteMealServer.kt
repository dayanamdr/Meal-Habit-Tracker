package com.example.mealhabittracker.feature_meal.domain.use_case.server_use_case

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.ServerMealRepository

class DeleteMealServer(
    private val repository: ServerMealRepository
) {
    suspend operator fun invoke(meal: Meal) {
        repository.deleteMeal(meal)
    }
}