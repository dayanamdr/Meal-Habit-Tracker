package com.example.mealhabittracker.feature_meal.data.repository

import android.util.Log
import com.example.mealhabittracker.feature_meal.data.client.MealService
import com.example.mealhabittracker.feature_meal.data.data_source.MealDao
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.Exception

class MealRepositoryImpl(
    private val dao: MealDao,
    private val mealService: MealService
): MealRepository {
     override suspend fun synchronizeData() {
        val localMeals = dao.getMeals().first()
        val remoteMealsResponse = mealService.retrieveAllMeals()

        if (remoteMealsResponse.isSuccessful) { // Retrieved data from the server successfully
            val remoteMeals = remoteMealsResponse.body()!!

            // Calculate differences

            // Meals that are in the local db and not on the server
            val additions = localMeals.filter { localItem ->
                remoteMeals.none { it.id == localItem.id }
            }
            // Meals that are on the server and not in the local db
            val deletions = remoteMeals.filter { remoteItem ->
                localMeals.none { it.id == remoteItem.id }
            }

            // Meals from local db that have at least on different field than their version from the server
            val uniqueRemoteIds = remoteMeals.map { it.id }.toSet()
            val updates = localMeals.filter { obj ->
                uniqueRemoteIds.contains(obj.id) && remoteMeals.find { it.id == obj.id }
                    ?.let { matchingObj ->
                        // Check if at least one other field has a different value
                        obj.name != matchingObj.name || obj.type != matchingObj.type || obj.calories != matchingObj.calories
                                || obj.protein != matchingObj.protein || obj.fats != matchingObj.fats || obj.carbs != matchingObj.carbs
                    } ?: false
            }

            Log.d("MealRepositoryImpl synchronizeData", "additions = $additions\n")
            Log.d("MealRepositoryImpl synchronizeData", "deletions = $deletions\n")
            Log.d("MealRepositoryImpl synchronizeData", "updates = $updates\n")

            // Update server data
            if (additions.isNotEmpty()) {
                mealService.addMeals(additions)
            }
            if (deletions.isNotEmpty()) {
                mealService.deleteMeals(deletions)
            }
            if (updates.isNotEmpty()) {
                mealService.updateAll(updates)
            }
        } else {
            Log.d("synchronizeData", "Could not synchronize data with the server.")
            throw Exception("Could not synchronize data with the server.")
        }
    }

    override fun getMeals(): Flow<List<Meal>> {
        return dao.getMeals()
    }

    override suspend fun getMealById(id: Int): Meal? {
        return dao.getMealById(id)
    }

    override suspend fun insertMeal(meal: Meal) {
        Log.d("insertMeal", "Add meal to DB")
        Log.d("insertMeal", "id: " + meal.id)
        return dao.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: Meal) {
        Log.d("deleteMeal", "Delete meal from DB")
        Log.d("deleteMeal", "id: " + meal.id)
        return dao.deleteMeal(meal)
    }
}