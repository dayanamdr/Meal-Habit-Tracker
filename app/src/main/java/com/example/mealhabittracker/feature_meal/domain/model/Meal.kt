package com.example.mealhabittracker.feature_meal.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val type: String,
    val calories: Int,
    val protein: Int,
    val fats: Int,
    val carbs: Int,
    val timestamp: Long
) { }

class InvalidMealException(message: String): Exception(message)