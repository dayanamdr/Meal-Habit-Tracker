package com.example.mealhabittracker.feature_meal.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealhabittracker.feature_meal.presentation.add_edit_meal.AddEditMealScreen
import com.example.mealhabittracker.feature_meal.presentation.meal.MealsScreen
import com.example.mealhabittracker.feature_meal.presentation.util.Screen
import com.example.mealhabittracker.ui.theme.MealHabitTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealHabitTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MealsScreen.route
                    ) {
                        composable(route = Screen.MealsScreen.route) {
                            MealsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddMealScreen.route +
                                    "?mealId={mealId}",
                            arguments = listOf(
                                navArgument(
                                    name = "mealId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditMealScreen(
                                navController = navController
                            )

                        }
                    }
                }
            }
        }
    }
}