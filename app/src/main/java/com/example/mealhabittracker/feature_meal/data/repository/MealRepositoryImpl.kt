package com.example.mealhabittracker.feature_meal.data.repository

import com.example.mealhabittracker.feature_meal.data.data_source.MealDao
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow

class MealRepositoryImpl(
    private val dao: MealDao
): MealRepository {
    override fun getMeals(): Flow<List<Meal>> {
        return dao.getMeals()
    }

    override suspend fun getMealById(id: Int): Meal? {
        return dao.getMealById(id)
    }

    override suspend fun insertMeal(meal: Meal) {
        return dao.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: Meal) {
        return dao.deleteMeal(meal)
    }
}