package com.example.mealhabittracker.feature_meal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(
    val name: String,
    val type: String,
    val calories: Int,
    val protein: Int,
    val fats: Int,
    val carbs: Int,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
) { }

class InvalidMealException(message: String): Exception(message)