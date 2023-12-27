package com.example.mealhabittracker.feature_meal.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
