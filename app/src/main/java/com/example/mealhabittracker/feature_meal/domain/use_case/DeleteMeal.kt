package com.example.mealhabittracker.feature_meal.domain.use_case

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository

class DeleteMeal(
    private val repository: MealRepository
) {

    suspend operator fun invoke(note: Meal) {
        repository.deleteMeal(note)
    }
}