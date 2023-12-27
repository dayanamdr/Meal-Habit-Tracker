package com.example.mealhabittracker.feature_meal.domain.use_case

import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.repository.MealRepository
import com.example.mealhabittracker.feature_meal.domain.util.MealOrder
import com.example.mealhabittracker.feature_meal.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMeals(
    private val repository: MealRepository
) {
    operator fun invoke(mealOrder: MealOrder = MealOrder.Date(OrderType.Descending)
    ): Flow<List<Meal>> {
        return repository.getMeals().map { meals ->
            when(mealOrder.orderType) {
                is OrderType.Ascending -> {
                    when (mealOrder) {
                        is MealOrder.Name -> {meals.sortedBy { it.name.lowercase() }}
                        is MealOrder.Type -> {meals.sortedBy { it.type.lowercase() }}
                        is MealOrder.Date -> {meals.sortedBy { it.timestamp }}
                    }
                }
                is OrderType.Descending -> {
                    when (mealOrder) {
                        is MealOrder.Name -> {meals.sortedByDescending { it.name.lowercase() }}
                        is MealOrder.Type -> {meals.sortedByDescending { it.type.lowercase() }}
                        is MealOrder.Date -> {meals.sortedByDescending { it.timestamp }}
                    }
                }
            }
        }
    }
}