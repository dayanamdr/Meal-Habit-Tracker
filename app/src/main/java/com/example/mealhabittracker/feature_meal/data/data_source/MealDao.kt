package com.example.mealhabittracker.feature_meal.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM meal")
    fun getMeals(): Flow<List<Meal>>

    @Query("SELECT * FROM meal WHERE id = :id")
    suspend fun getMealById(id: Int): Meal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)
}