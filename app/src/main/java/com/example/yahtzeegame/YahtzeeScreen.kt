package com.example.yahtzeegame

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun YahtzeeScreen(
    viewModel: YahtzeeViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Yahtzee",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            viewModel.diceValues.forEach { value ->

                Die(
                    value = value
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                viewModel.rollWithCoroutine()
            },
            enabled = !viewModel.isRolling
        ) {
            Text(
                text = if (viewModel.isRolling) {
                    "Rolling..."
                } else {
                    "Roll"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        if (viewModel.categoryScores.isNotEmpty()) {

            Text(
                text = "Available Categories",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            viewModel.categoryScores.forEach { result ->

                Text(
                    text = "${formatCategory(result.category)}: ${result.score}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun Die(value: Int) {

    val diceImage = when (value) {

        1 -> R.drawable.die_1
        2 -> R.drawable.die_2
        3 -> R.drawable.die_3
        4 -> R.drawable.die_4
        5 -> R.drawable.die_5
        6 -> R.drawable.die_6

        else -> R.drawable.die_1
    }

    Image(
        painter = painterResource(
            id = diceImage
        ),
        contentDescription = "Dice showing $value",
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp)
    )
}

fun formatCategory(
    category: YahtzeeCategory
): String {

    return when (category) {

        YahtzeeCategory.THREE_OF_A_KIND ->
            "Three of a Kind"

        YahtzeeCategory.FOUR_OF_A_KIND ->
            "Four of a Kind"

        YahtzeeCategory.FULL_HOUSE ->
            "Full House"

        YahtzeeCategory.SMALL_STRAIGHT ->
            "Small Straight"

        YahtzeeCategory.LARGE_STRAIGHT ->
            "Large Straight"

        YahtzeeCategory.YAHTZEE ->
            "Yahtzee"

        YahtzeeCategory.CHANCE ->
            "Chance"
    }
}