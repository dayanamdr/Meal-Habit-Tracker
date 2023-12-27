package com.example.mealhabittracker.feature_meal.presentation.meal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mealhabittracker.feature_meal.domain.model.Meal

@Composable
fun MealItem(
    meal: Meal,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = meal.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = meal.type, style = MaterialTheme.typography.headlineSmall)
            }
            Spacer(modifier = Modifier.padding(top = 6.dp))
            Text(text = "Calories: ${meal.calories} kcal", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Protein: ${meal.protein} g", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Fats:  ${meal.fats} g", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Carbs:  ${meal.carbs} g", style = MaterialTheme.typography.bodyLarge)
        }
    }
}