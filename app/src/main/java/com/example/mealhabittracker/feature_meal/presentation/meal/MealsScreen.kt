package com.example.mealhabittracker.feature_meal.presentation.meal

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealhabittracker.R
import com.example.mealhabittracker.feature_meal.presentation.meal.components.DeleteMealItem
import com.example.mealhabittracker.feature_meal.presentation.meal.components.OrderSection
import com.example.mealhabittracker.feature_meal.presentation.util.Screen
import com.example.mealhabittracker.feature_meal.utils.ConnectionStatus
import com.example.mealhabittracker.feature_meal.utils.currentConnectivityStatus
import com.example.mealhabittracker.feature_meal.utils.observeConnectivityAsFlow
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MealsScreen(
    navController: NavController,
    viewModel: MealsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MealsViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddMealScreen.route) },
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Your meals", style = MaterialTheme.typography.headlineSmall)
                IconButton(onClick = { viewModel.onEvent(MealsEvent.ToggleOrderSection) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_tune_24),
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    mealOrder = state.mealOrder,
                    onChangeOrder = {
                        viewModel.onEvent(MealsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            CheckConnectivityStatus()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyListState()
            ) {
                itemsIndexed(items = state.meals, key = {_, listItem -> listItem.hashCode()}) {
                        _, meal ->
                        DeleteMealItem(
                            meal = meal,
                            onDelete = { viewModel.onEvent(MealsEvent.DeleteMeal(meal)) },
                            onClickMealItem = {
                                navController.navigate(Screen.AddMealScreen.route +
                                        "?mealId=${meal.id}"
                                )}
                        )
                }
            }
        }
    }
}

/**
 * Helper component to check the internet connection in real time.
 */
@Composable
fun CheckConnectivityStatus() {
    val connection by connectivityStatus()
    val isConnected = connection === ConnectionStatus.Available
    if (isConnected) {
        Text("INTERNET ON")
    } else {
        Text(text = "INTERNET OFF")
    }
}

@Composable
fun connectivityStatus(): State<ConnectionStatus> {
    val mCtx = LocalContext.current

    return produceState(initialValue = mCtx.currentConnectivityStatus) {
        mCtx.observeConnectivityAsFlow().collect{ value = it }
    }
}