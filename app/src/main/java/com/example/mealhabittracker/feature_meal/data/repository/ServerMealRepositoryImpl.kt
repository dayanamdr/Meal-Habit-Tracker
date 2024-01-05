package com.example.mealhabittracker.feature_meal.data.repository

import android.util.Log
import com.example.mealhabittracker.feature_meal.data.client.MealService
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.ServerMealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerMealRepositoryImpl(private val mealService: MealService): ServerMealRepository {
    override suspend fun getMeals(): Flow<List<Meal>> {
        val remoteMealsResponse = mealService.retrieveAllMeals()

        if(remoteMealsResponse.isSuccessful) {
            return flow { emit(remoteMealsResponse.body()!!) }
        } else {
            //handle error here?
            Log.d("getMeals","Could not GET the meals from the server.")
            //throw Exception("Could not GET the meals from the server.")
        }
        return flow { emit(emptyList()) }
    }

    override suspend fun getMealById(id: Int): Meal? {
        TODO("Not yet implemented")
    }

    override suspend fun insertMeal(meal: Meal) {
        if (meal.id == null) { // add operation
            val addResponse = mealService.createMeal(meal)
            if (!addResponse.isSuccessful) {
                Log.d("insertMeal","Could not ADD the meal to the server.")
                throw Exception("Could not ADD the meal to the server.")
            }
        } else {
            val updateResponse = mealService.updateMeal(meal.id, meal)
            if (!updateResponse.isSuccessful) { // update operation
                Log.d("insertMeal","Could not UPDATE the meal from the server.")
                throw Exception("Could not UPDATE the meal from the server.")
            }
        }
    }

    override suspend fun deleteMeal(meal: Meal) {
        val deleteResponse = mealService.deleteMeal(meal.id)
        if (!deleteResponse.isSuccessful) {
            Log.d("deleteMeal","Could not DELETE the meal from the server.")
            throw Exception("Could not DELETE the meal from the server.")
        }
    }
}