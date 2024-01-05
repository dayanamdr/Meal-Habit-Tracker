package com.example.mealhabittracker.feature_meal.domain.use_case.server_use_case

import com.example.mealhabittracker.feature_meal.domain.model.InvalidMealException
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository
import com.example.mealhabittracker.feature_meal.domain.repository.ServerMealRepository

class AddMealServer(
    private val repository: ServerMealRepository
) {
    @Throws(InvalidMealException::class)
    suspend operator fun invoke(meal: Meal) {
        if (meal.name.isBlank()) {
            throw InvalidMealException("The name of the meal can't be empty.")
        }
        if (meal.type.isBlank()) {
            throw InvalidMealException("The type of the meal can't be empty.")
        }
        repository.insertMeal(meal)
    }
}