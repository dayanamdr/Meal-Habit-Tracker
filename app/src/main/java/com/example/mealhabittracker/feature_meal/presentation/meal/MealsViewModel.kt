package com.example.mealhabittracker.feature_meal.presentation.meal

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.use_case.MealUseCases
import com.example.mealhabittracker.feature_meal.domain.util.MealOrder
import com.example.mealhabittracker.feature_meal.domain.util.OrderType
import com.example.mealhabittracker.feature_meal.utils.ConnectionStatus
import com.example.mealhabittracker.feature_meal.utils.currentConnectivityStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealUseCase: MealUseCases,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(MealsState())
    val state: State<MealsState> = _state

    private var recentlyDeletedMeal: Meal? = null
    private var getMealsJob: Job? = null

    init {
        viewModelScope.launch {
            try {
                mealUseCase.synchronizeData()
            } catch(e: Exception) {
                Log.d("init MealsViewModel", "Could not synchronize data with the server.")
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = "No internet. Could not load server data."
                    )
                )
            }
            getMeals(MealOrder.Date(OrderType.Descending))
        }

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
                viewModelScope.launch {
                    try {
                        mealUseCase.deleteMeal(event.meal)
                        recentlyDeletedMeal = event.meal

                        if (context.currentConnectivityStatus == ConnectionStatus.Available) {
                            mealUseCase.deleteMealServer(event.meal)
                        } else {
                            Log.d("delete MealsViewModel", "No internet. Deleted locally.")
                            // maybe use a Toast instead?
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = "No internet. Deleted locally."
                                )
                            )
                        }
                    } catch(e: Exception) {
                        Log.d("delete MealsViewModel", "Could not delete meal from the server.")
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Could not delete meal from the server."
                            )
                        )
                    }
                }
            }
            is MealsEvent.RestoreMeal -> {
                viewModelScope.launch {
                    try {
                        mealUseCase.addMeal(recentlyDeletedMeal?: return@launch)
                        // checks internet connection
                        if (context.currentConnectivityStatus == ConnectionStatus.Available && recentlyDeletedMeal != null) {
                            mealUseCase.addMealServer(recentlyDeletedMeal!!)
                        } else {
                            Log.d("restore MealsViewModel", "No internet. Added locally.")
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = "No internet. Added locally."
                                )
                            )
                        }
                        recentlyDeletedMeal = null
                    } catch(e: Exception) {
                        Log.d("restoreMeal MealsViewModel", "Could not restore meal from the server.")
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Could not restore meal from the server."
                            )
                        )
                    }
                }
            }
            is MealsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is MealsEvent.NetworkConnectivity -> {
                viewModelScope.launch {
                    if (context.currentConnectivityStatus == ConnectionStatus.Available) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Internet ON. Data synced."
                            )
                        )
                        mealUseCase.synchronizeData()
                    } else {
                        Log.d("networkConnectivity MealsViewModel", "No internet. Could not sync.")
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "No internet. Could not sync."
                            )
                        )
                    }
                }
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

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
    }
}