package com.example.mealhabittracker.feature_meal.domain.repository

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    fun getMeals(): Flow<List<Meal>>

    suspend fun getMealById(id: Int): Meal?

    suspend fun insertMeal(note: Meal)

    suspend fun deleteMeal(note: Meal)
}