package com.example.mealhabittracker.feature_meal.domain.repository

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    suspend fun synchronizeData()

    fun getMeals(): Flow<List<Meal>>

    suspend fun getMealById(id: Int): Meal?

    suspend fun insertMeal(meal: Meal)

    suspend fun deleteMeal(meal: Meal)
}