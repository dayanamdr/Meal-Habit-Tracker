package com.example.mealhabittracker.feature_meal.domain.util

sealed class MealOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): MealOrder(orderType)
    class Type(orderType: OrderType): MealOrder(orderType)
    class Date(orderType: OrderType): MealOrder(orderType)

    fun copy(orderType: OrderType): MealOrder {
        return when(this) {
            is Name -> Name(orderType)
            is Type -> Type(orderType)
            is Date -> Date(orderType)
        }
    }
}
