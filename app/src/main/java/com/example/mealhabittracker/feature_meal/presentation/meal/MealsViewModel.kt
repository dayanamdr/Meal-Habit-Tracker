package com.example.mealhabittracker.feature_meal.presentation.meal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.use_case.MealUseCases
import com.example.mealhabittracker.feature_meal.domain.util.MealOrder
import com.example.mealhabittracker.feature_meal.domain.util.OrderType
import com.example.mealhabittracker.feature_meal.presentation.add_edit_meal.AddEditMealViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealUseCase: MealUseCases
): ViewModel() {

    private val _state = mutableStateOf(MealsState())
    val state: State<MealsState> = _state

    private var recentlyDeletedMeal: Meal? = null
    private var getMealsJob: Job? = null

    init {
        getMeals(MealOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: MealsEvent) {
        when(event) {
            is MealsEvent.Order -> {
                if (state.value.mealOrder::class == event.mealOrder::class &&
                    state.value.mealOrder.orderType == event.mealOrder.orderType
                    ) {
                    return
                }
                getMeals(event.mealOrder)
            }
            is MealsEvent.DeleteMeal -> {
                try {
                    viewModelScope.launch {
                        mealUseCase.deleteMeal(event.meal)
                        recentlyDeletedMeal = event.meal
                    }
                } catch(e: Exception) {
                    print("Delete exception\n")
                }
            }
            is MealsEvent.RestoreMeal -> {
                viewModelScope.launch {
                    mealUseCase.addMeal(recentlyDeletedMeal?: return@launch)
                    recentlyDeletedMeal = null
                }
            }
            is MealsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getMeals(mealOrder: MealOrder) {
        getMealsJob?.cancel()
        getMealsJob = mealUseCase.getMeals(mealOrder)
            .onEach { meals ->
                _state.value = state.value.copy(meals = meals,
                    mealOrder = mealOrder)
            }
            .launchIn(viewModelScope)
    }
}