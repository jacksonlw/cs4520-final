package com.cs4520.brainflex

import android.util.Log
import androidx.compose.ui.unit.Constraints.Companion.Infinity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlin.random.Random

enum class GameState {
    IN_PROGRESS,
    GAME_OVER
}

class GameViewModel() : ViewModel() {

    // repository to send score
    private var _currentLevel = MutableLiveData<Int>(1)
    val currentLevel: LiveData<Int> = _currentLevel

    private val _sequence =
        MutableLiveData<List<Int>>(currentLevel.value?.let { generateSequence(it) })
    val sequence: LiveData<List<Int>> = _sequence

    private val _gameState = MutableLiveData<GameState>(GameState.IN_PROGRESS)
    val gameState: LiveData<GameState> = _gameState

    private var expectedIndex = 0

    fun startNewGame(currentLevel: Int) {
        _currentLevel.value = currentLevel
        val newSequence = generateSequence(currentLevel)
        _sequence.value = newSequence
        _gameState.value = GameState.IN_PROGRESS
    }

    private fun generateSequence(currentLevel: Int): List<Int> {
        // current score equals to the length of the sequence
        val random = Random.Default
        return List(currentLevel) {
            random.nextInt(9) // Generates random number from 0 to 8
        }
    }

    fun nextStep(cellNumber: Int, navHostController: NavHostController) {
        if (cellNumber == (sequence.value?.get(expectedIndex) ?: Infinity)) {
            Log.d("AM I HERE", sequence.value?.get(expectedIndex).toString())

            // if this is the last digit in the sequence
            if (expectedIndex == (sequence.value?.size?.minus(1) ?: 0)) {
                // reset  user input progress for the next round
                expectedIndex = 0

                // next level
                val newValue = currentLevel.value!!.plus(1)
                _currentLevel.value = newValue

                // generate new sequence
                val newSequence = generateSequence(currentLevel.value!!)
                _sequence.value = newSequence

                // game state is still in progress
            } else {
                Log.d("Next", sequence.value?.get(expectedIndex).toString())

                // if it's not the last element/digit
                // increase expected Index
                expectedIndex++
            }
            // if the user clicked the wrong button
        } else {
            Log.d("GAME OVER", "GAME OVER")
            _currentLevel.value = 1
            _sequence.value = currentLevel.value?.let { generateSequence(it) }
            navHostController.navigate(Screen.INFORMATION.name)
        }
    }
}