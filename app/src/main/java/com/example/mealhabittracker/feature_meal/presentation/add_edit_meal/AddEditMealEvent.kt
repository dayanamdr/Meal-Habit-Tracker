package com.example.mealhabittracker.feature_meal.presentation.add_edit_meal

import androidx.compose.ui.focus.FocusState

sealed class AddEditMealEvent {
    data class EnteredName(val value: String): AddEditMealEvent()
    data class ChangeNameFocus(val focusState: FocusState): AddEditMealEvent()
    data class EnteredType(val value: String): AddEditMealEvent()
    data class ChangeTypeFocus(val focusState: FocusState): AddEditMealEvent()
    data class EnteredCalories(val value: String): AddEditMealEvent()
    data class ChangeCaloriesFocus(val focusState: FocusState): AddEditMealEvent()
    data class EnteredProtein(val value: String): AddEditMealEvent()
    data class ChangeProteinFocus(val focusState: FocusState): AddEditMealEvent()
    data class EnteredFats(val value: String): AddEditMealEvent()
    data class ChangeFatsFocus(val focusState: FocusState): AddEditMealEvent()
    data class EnteredCarbs(val value: String): AddEditMealEvent()
    data class ChangeCarbsFocus(val focusState: FocusState): AddEditMealEvent()
    object SaveMeal: AddEditMealEvent()
    object CancelSaveMeal: AddEditMealEvent()
}
