package com.example.mealhabittracker.feature_meal.domain.use_case

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository

class GetMeal(
    private val repository: MealRepository
) {
    suspend operator fun invoke(id: Int): Meal? {
        return repository.getMealById(id)
    }
}