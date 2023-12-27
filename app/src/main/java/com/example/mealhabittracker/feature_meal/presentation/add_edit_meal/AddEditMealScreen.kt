package com.example.mealhabittracker.feature_meal.presentation.add_edit_meal

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealhabittracker.R
import com.example.mealhabittracker.feature_meal.presentation.add_edit_meal.components.ScreenTitle
import com.example.mealhabittracker.feature_meal.presentation.add_edit_meal.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditMealScreen(
    navController: NavController,
    viewModel: AddEditMealViewModel = hiltViewModel()
) {
    val nameState = viewModel.mealName.value
    val typeState = viewModel.mealType.value
    val caloriesState = viewModel.mealCalories.value
    val proteinState = viewModel.mealProtein.value
    val fatsState = viewModel.mealFats.value
    val carbsState = viewModel.mealCarbs.value
    val mealId = viewModel.currentMealId

    val titleText = if (mealId == null) stringResource(R.string.add_meal_screen_title)
        else stringResource(R.string.update_meal_screen_title)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditMealViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
                is AddEditMealViewModel.UiEvent.SaveMeal -> {
                    navController.navigateUp()
                }
                is AddEditMealViewModel.UiEvent.CancelSaveMeal -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(titleText = titleText)

            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = nameState.text,
                hint = nameState.hint,
                label = stringResource(R.string.meal_name_label),
                onValueChange = {
                    viewModel.onEvent(AddEditMealEvent.EnteredName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMealEvent.ChangeNameFocus(it))
                },
                isHintVisible = nameState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = typeState.text,
                hint = typeState.hint,
                label = stringResource(R.string.meal_type_label),
                onValueChange = {
                    viewModel.onEvent(AddEditMealEvent.EnteredType(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMealEvent.ChangeTypeFocus(it))
                },
                isHintVisible = typeState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = caloriesState.text,
                hint = caloriesState.hint,
                label = stringResource(R.string.meal_calories_label),
                isNumberField = true,
                onValueChange = {
                    viewModel.onEvent(AddEditMealEvent.EnteredCalories(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMealEvent.ChangeCaloriesFocus(it))
                },
                isHintVisible = caloriesState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = proteinState.text,
                hint = proteinState.hint,
                label = stringResource(R.string.meal_protein_label),
                isNumberField = true,
                onValueChange = {
                    viewModel.onEvent(AddEditMealEvent.EnteredProtein(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMealEvent.ChangeProteinFocus(it))
                },
                isHintVisible = proteinState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = fatsState.text,
                hint = fatsState.hint,
                label = stringResource(R.string.meal_fats_label),
                isNumberField = true,
                onValueChange = {
                    viewModel.onEvent(AddEditMealEvent.EnteredFats(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMealEvent.ChangeFatsFocus(it))
                },
                isHintVisible = fatsState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            TransparentHintTextField(
                text = carbsState.text,
                hint = carbsState.hint,
                label = stringResource(R.string.meal_carbs_label),
                isNumberField = true,
                onValueChange = {
                    viewModel.onEvent(AddEditMealEvent.EnteredCarbs(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMealEvent.ChangeCarbsFocus(it))
                },
                isHintVisible = carbsState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
            )
            Spacer(Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(AddEditMealEvent.SaveMeal) }
            ) {
                Text(stringResource(R.string.save_meal_button))
            }
            Spacer(Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(AddEditMealEvent.CancelSaveMeal) }
            ) {
                Text(stringResource(R.string.cancel_save_meal_button))
            }
        }
    }
}