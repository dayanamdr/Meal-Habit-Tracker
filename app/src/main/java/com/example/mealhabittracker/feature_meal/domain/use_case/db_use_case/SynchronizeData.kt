package com.example.mealhabittracker.feature_meal.domain.use_case.db_use_case

import android.util.Log
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository

class SynchronizeData(
    private val repository: MealRepository
) {
    suspend operator fun invoke() {
        Log.i("SynchronizeData UseCase", "SYNC USE CASE")
        return repository.synchronizeData()
    }
}