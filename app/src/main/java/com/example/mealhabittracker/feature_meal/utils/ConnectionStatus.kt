package com.example.mealhabittracker.feature_meal.utils

sealed class ConnectionStatus {
    object Available: ConnectionStatus()
    object Unavailable: ConnectionStatus()
}
