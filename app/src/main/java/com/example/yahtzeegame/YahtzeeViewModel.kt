package com.example.yahtzeegame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class YahtzeeViewModel : ViewModel() {

    var diceValues by mutableStateOf(
        listOf(1, 1, 1, 1, 1)
    )
        private set

    var categoryScores by mutableStateOf(
        emptyList<CategoryScore>()
    )
        private set

    var isRolling by mutableStateOf(false)
        private set

    private fun rollDice(): Int {
        return Random.nextInt(1, 7)
    }

    fun rollOnce() {

        diceValues = List(5) {
            rollDice()
        }

        evaluateDice()
    }

    /*
     * Traditional Thread implementation.
     *
     * This is included because the laboratory requires
     * demonstrating the Thread approach before using
     * a Coroutine.
     */
    fun rollWithoutCoroutine() {

        if (isRolling) return

        isRolling = true

        Thread {

            repeat(10) {

                val newValues = List(5) {
                    rollDice()
                }

                android.os.Handler(
                    android.os.Looper.getMainLooper()
                ).post {

                    diceValues = newValues
                }

                Thread.sleep(100)
            }

            android.os.Handler(
                android.os.Looper.getMainLooper()
            ).post {

                evaluateDice()
                isRolling = false
            }

        }.start()
    }

    /*
     * Coroutine implementation.
     *
     * This is the final rolling method used by the UI.
     */
    fun rollWithCoroutine() {

        if (isRolling) return

        isRolling = true

        viewModelScope.launch {

            repeat(10) {

                diceValues = List(5) {
                    rollDice()
                }

                delay(100)
            }

            evaluateDice()

            isRolling = false
        }
    }

    private fun evaluateDice() {

        categoryScores =
            DiceRules
                .getAvailableCategories(diceValues)
                .map { category ->

                    CategoryScore(
                        category = category,
                        score = DiceRules.scoreFor(
                            category = category,
                            dice = diceValues
                        )
                    )
                }
    }
}