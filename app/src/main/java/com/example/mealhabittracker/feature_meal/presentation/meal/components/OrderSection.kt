package com.example.mealhabittracker.feature_meal.presentation.meal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mealhabittracker.feature_meal.domain.util.MealOrder
import com.example.mealhabittracker.feature_meal.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    mealOrder: MealOrder = MealOrder.Date(OrderType.Descending),
    onChangeOrder: (MealOrder) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Name",
                selected = mealOrder is MealOrder.Name ,
                onSelect = { onChangeOrder(MealOrder.Name(mealOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Type",
                selected = mealOrder is MealOrder.Type ,
                onSelect = { onChangeOrder(MealOrder.Type(mealOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = mealOrder is MealOrder.Date ,
                onSelect = { onChangeOrder(MealOrder.Date(mealOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = mealOrder.orderType is OrderType.Ascending ,
                onSelect = { onChangeOrder(mealOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = mealOrder.orderType is OrderType.Descending ,
                onSelect = { onChangeOrder(mealOrder.copy(OrderType.Descending)) }
            )
        }
    }
}