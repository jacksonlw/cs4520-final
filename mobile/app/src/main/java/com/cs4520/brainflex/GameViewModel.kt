package com.cs4520.brainflex

import android.util.Log
import androidx.compose.ui.unit.Constraints.Companion.Infinity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import kotlin.math.exp
import kotlin.random.Random

enum class GameState {
    IN_PROGRESS,
    GAME_OVER
}
class GameViewModel() : ViewModel() {

    // repository to send score
    private var _currentLevel = MutableLiveData<Int>(1)
    val currentLevel: LiveData<Int> = _currentLevel

    private val _sequence = MutableLiveData<List<Int>>(listOf())
    val sequence: LiveData<List<Int>> = _sequence

    private val _gameState = MutableLiveData<GameState>(GameState.IN_PROGRESS)
    val gameState: LiveData<GameState> = _gameState
    private var expectedIndex = 0

    init {
        generateSequence(currentLevel.value!!)
    }
    private fun generateSequence(currentLevel: Int) {
        // current score equals to the length of the sequence
        val random = Random.Default
        _sequence.value =  List(currentLevel) {
            random.nextInt(9) // Generates random number from 0 to 8
        }
        Log.d("current level", currentLevel.toString())
        Log.d("SEQUENCE GET UPDATED?", sequence.value.toString())
    }

    fun nextStep(cellNumber: Int) {
        Log.d("What I Click", cellNumber.toString())
        if(cellNumber == (sequence.value?.get(expectedIndex) ?: Infinity)){
            Log.d("AM I HERE", sequence.value?.get(expectedIndex).toString())
            if (expectedIndex == (sequence.value?.size?.minus(1) ?: 0)) {
                // entire sequence was entered correctly
                // reset  user input progress for the next round
                expectedIndex = 0
                val newValue = currentLevel.value!!.plus(1)
                _currentLevel.value = newValue
                generateSequence(currentLevel.value!!)
            } else {
                expectedIndex++
            }
        }  else {
            _gameState.value = GameState.GAME_OVER
        }
    }

    fun resetGame() {
        _sequence.value = listOf<Int>()
        _currentLevel.value = 1
        currentLevel.value?.let { generateSequence(it) }
    }
}