package com.cs4520.brainflex

import androidx.compose.ui.unit.Constraints.Companion.Infinity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import kotlin.random.Random

enum class GameState {
    IN_PROGRESS,
    GAME_OVER
}
class GameViewModel() : ViewModel() {

    // repository to send score
    private var _currentLevel = MutableLiveData<Int>(1)
    val currentLevel: LiveData<Int> = _currentLevel

    private val _sequence = MutableLiveData<List<Int>>()
    val sequence: LiveData<List<Int>> = _sequence

    private val _gameState = MutableLiveData<GameState>(GameState.IN_PROGRESS)
    val gameState: LiveData<GameState> = _gameState
    private var nextExpectedIndex = 0

    init {
        generateSequence(currentLevel.value!!)
    }
    private fun generateSequence(currentLevel: Int) {
        // current score equals to the length of the sequence
        val random = Random.Default
        _sequence.value =  List(currentLevel) {
            random.nextInt(9) // Generates random number from 0 to 8
        }
    }

    fun checkUserInput(cellNumber: Int): Boolean {
        if(cellNumber == (sequence.value?.get(nextExpectedIndex) ?: Infinity)){
            nextExpectedIndex++
            if (nextExpectedIndex == sequence.value?.size) {
                // entire sequence was entered correctly
                // reset  user input progress for the next round
                nextExpectedIndex = 0
                return true
            }
        }
        return false
    }

    fun nextStep(input: Int) {
        if(checkUserInput(input)) {
            val newValue = currentLevel.value!!.plus(1)
            _currentLevel.value = newValue
            _gameState.value = GameState.IN_PROGRESS
            generateSequence(currentLevel.value!!)
        } else {
            _gameState.value = GameState.GAME_OVER
            // send score here
        }
    }
}