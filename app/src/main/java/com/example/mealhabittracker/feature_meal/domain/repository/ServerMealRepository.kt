package com.example.mealhabittracker.feature_meal.domain.repository

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface ServerMealRepository {
    suspend fun getMeals(): Flow<List<Meal>>

    suspend fun getMealById(id: Int): Meal?

    suspend fun insertMeal(meal: Meal)

    suspend fun deleteMeal(meal: Meal)
}