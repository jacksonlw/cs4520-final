package com.cs4520.brainflex.view.game

import android.util.Log
import androidx.compose.ui.unit.Constraints.Companion.Infinity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.cs4520.brainflex.Screen
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepository
import kotlin.random.Random

enum class GameState {
    GAME_OBSERVE,
    GAME_TAP,
    GAME_OVER
}

class GameViewModel(private val apiClient: ApiClient, private val userRepo: UserRepository) : ViewModel() {
    // repository to send score
    private var _currentLevel = MutableLiveData<Int>(1)
    val currentLevel: LiveData<Int> = _currentLevel

    private val _sequence =
        MutableLiveData<List<Int>>(currentLevel.value?.let { generateSequence(it) })
    val sequence: LiveData<List<Int>> = _sequence

    private val _gameState = MutableLiveData<GameState>(GameState.GAME_OBSERVE)
    val gameState: LiveData<GameState> = _gameState

    private var expectedIndex = 0

    fun startNewGame(currentLevel: Int) {
        _currentLevel.value = currentLevel
        val newSequence = generateSequence(currentLevel)
        _sequence.value = newSequence
        _gameState.value = GameState.GAME_OBSERVE
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

                // set game to observe mode
                _gameState.value = GameState.GAME_OBSERVE
            } else {
                Log.d("Next", sequence.value?.get(expectedIndex).toString())

                // if it's not the last element/digit
                // increase expected Index
                expectedIndex++
            }
            // if the user clicked the wrong button
        } else {
            Log.d("GAME OVER", "GAME OVER")
            val score = _currentLevel.value
            _currentLevel.value = 1
            _sequence.value = listOf()
            navHostController.navigate(Screen.GAMESTART.name + "/$score")
        }
    }

    fun updateStatus(newState: GameState){
        _gameState.value = newState
    }
}