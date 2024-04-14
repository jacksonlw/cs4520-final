package com.cs4520.brainflex

import androidx.compose.ui.unit.Constraints.Companion.Infinity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager

enum class GameState {
    IN_PROGRESS,
    GAME_OVER
}
class GameViewModel() : ViewModel() {

    // repository to send score
    private var _currentScore = MutableLiveData<Int>()
    val currentScore: LiveData<Int> = _currentScore

    private val _sequence = MutableLiveData<List<Int>>()
    val sequence: LiveData<List<Int>> = _sequence

    private val _gameState = MutableLiveData<GameState>(GameState.IN_PROGRESS)
    val gameState: LiveData<GameState> = _gameState
    private var nextExpectedIndex = 0


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
            val newValue = currentScore.value!!.plus(1)
            _currentScore.value = newValue
            _gameState.value = GameState.IN_PROGRESS
        } else {
            _gameState.value = GameState.GAME_OVER
            // send score here
        }
    }
}