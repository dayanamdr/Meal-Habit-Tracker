package com.example.mealhabittracker.feature_meal.domain.use_case

import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.AddMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.DeleteMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.GetMeal
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.GetMeals
import com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case.SynchronizeData
import com.example.mealhabittracker.feature_meal.domain.use_case.server_use_case.AddMealServer
import com.example.mealhabittracker.feature_meal.domain.use_case.server_use_case.DeleteMealServer

data class MealUseCases(
    val getMeal: GetMeal,
    val getMeals: GetMeals,
    val addMeal : AddMeal,
    val deleteMeal: DeleteMeal,
    val synchronizeData : SynchronizeData,
    val addMealServer: AddMealServer,
    val deleteMealServer: DeleteMealServer,
)
