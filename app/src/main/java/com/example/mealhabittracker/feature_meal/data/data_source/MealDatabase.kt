package com.example.mealhabittracker.feature_meal.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealhabittracker.feature_meal.domain.model.Meal

@Database(
    entities = [Meal::class],
    version = 1,
    exportSchema = false
)
abstract class MealDatabase: RoomDatabase() {

    abstract val mealDao: MealDao

    companion object {
        const val DATABASE_NAME = "meals_db"
    }
}