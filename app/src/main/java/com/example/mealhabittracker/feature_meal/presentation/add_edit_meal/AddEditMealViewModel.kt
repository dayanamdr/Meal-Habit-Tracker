package com.example.mealhabittracker.feature_meal.presentation.add_edit_meal

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealhabittracker.feature_meal.domain.model.Meal
import com.example.mealhabittracker.feature_meal.domain.use_case.MealUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddEditMealViewModel @Inject constructor(
    private val mealUseCases: MealUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _mealName = mutableStateOf(MealTextFieldState(
        hint = "Enter name...",
    ))
    val mealName: State<MealTextFieldState> = _mealName

    private val _mealType = mutableStateOf(MealTextFieldState(
        hint = "Enter type (e.g. lunch, dinner...)"
    ))
    val mealType: State<MealTextFieldState> = _mealType

    private val _mealCalories = mutableStateOf(MealTextFieldState(
        hint = "Enter calories (e.g. 430)"
    ))
    val mealCalories: State<MealTextFieldState> = _mealCalories

    private val _mealProtein = mutableStateOf(MealTextFieldState(
        hint = "Enter protein (e.g. 23)"
    ))
    val mealProtein: State<MealTextFieldState> = _mealProtein

    private val _mealFats = mutableStateOf(MealTextFieldState(
        hint = "Enter fats (e.g. 12)"
    ))
    val mealFats: State<MealTextFieldState> = _mealFats

    private val _mealCarbs = mutableStateOf(MealTextFieldState(
        hint = "Enter carbs (e.g. 25)"
    ))
    val mealCarbs: State<MealTextFieldState> = _mealCarbs

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentMealId: Int? = null

    init {
        Log.i("AddEditMealViewModel", "started")
        Log.i("AddEditMealViewModel", "id: ${savedStateHandle.get<Int>("mealId")}")

        savedStateHandle.get<Int>("mealId")?.let {mealId ->
            if (mealId != -1) {
                Log.i("AddEditMealViewModel", "mealId: $mealId")
                viewModelScope.launch {
                    mealUseCases.getMeal(mealId)?.also { meal ->
                        currentMealId = meal.id
                        _mealName.value = mealName.value.copy(
                            text = meal.name,
                            isHintVisible = false
                        )
                        _mealType.value = mealType.value.copy(
                            text = meal.type,
                            isHintVisible = false
                        )
                        _mealCalories.value = mealCalories.value.copy(
                            text = meal.calories.toString(),
                            isHintVisible = false
                        )
                        _mealProtein.value = mealProtein.value.copy(
                            text = meal.protein.toString(),
                            isHintVisible = false
                        )
                        _mealFats.value = mealFats.value.copy(
                            text = meal.fats.toString(),
                            isHintVisible = false
                        )
                        _mealCarbs.value = mealCarbs.value.copy(
                            text = meal.carbs.toString(),
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditMealEvent) {
        when(event) {
            is AddEditMealEvent.EnteredName -> {
                _mealName.value = mealName.value.copy(
                    text = event.value
                )
            }
            is AddEditMealEvent.ChangeNameFocus -> {
                _mealName.value = mealName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                        mealName.value.text.isBlank()
                )
            }
            is AddEditMealEvent.EnteredType -> {
                _mealType.value = mealType.value.copy(
                    text = event.value,
                )
            }
            is AddEditMealEvent.ChangeTypeFocus -> {
                _mealType.value = mealType.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            mealName.value.text.isBlank()
                )
            }
            is AddEditMealEvent.EnteredCalories -> {
                _mealCalories.value = mealCalories.value.copy(
                    text = event.value,
                )
            }
            is AddEditMealEvent.ChangeCaloriesFocus -> {
                _mealCalories.value = mealCalories.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            mealName.value.text.isBlank()
                )
            }
            is AddEditMealEvent.EnteredProtein -> {
                _mealProtein.value = mealProtein.value.copy(
                    text = event.value,
                )
            }
            is AddEditMealEvent.ChangeProteinFocus -> {
                _mealProtein.value = mealProtein.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            mealName.value.text.isBlank()
                )
            }
            is AddEditMealEvent.EnteredFats -> {
                _mealFats.value = mealFats.value.copy(
                    text = event.value,
                )
            }
            is AddEditMealEvent.ChangeFatsFocus -> {
                _mealFats.value = mealFats.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            mealName.value.text.isBlank()
                )
            }
            is AddEditMealEvent.EnteredCarbs -> {
                _mealCarbs.value = mealCarbs.value.copy(
                    text = event.value,
                )
            }
            is AddEditMealEvent.ChangeCarbsFocus -> {
                _mealCarbs.value = mealCarbs.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            mealName.value.text.isBlank()
                )
            }
            is AddEditMealEvent.SaveMeal -> {
                viewModelScope.launch {
                    try {
                        mealUseCases.addMeal(
                            Meal(
                                name = mealName.value.text,
                                type =  mealType.value.text,
                                calories =  mealCalories.value.text.toInt(),
                                protein = mealProtein.value.text.toInt(),
                                fats = mealFats.value.text.toInt(),
                                carbs = mealCarbs.value.text.toInt(),
                                timestamp = System.currentTimeMillis(),
                                id = currentMealId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveMeal)
                    } catch(e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Could not save meal."
                            )
                        )
                    }
                }
            }
            is AddEditMealEvent.CancelSaveMeal -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.CancelSaveMeal)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveMeal: UiEvent()
        object CancelSaveMeal: UiEvent()
    }
}