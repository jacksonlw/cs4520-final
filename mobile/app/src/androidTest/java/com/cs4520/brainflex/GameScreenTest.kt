package com.cs4520.brainflex

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.brainflex.view.game.GameScreen
import com.cs4520.brainflex.view.game.GameState
import com.cs4520.brainflex.view.game.GameViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameScreenTest {
    private lateinit var viewModel: GameViewModel

    private val currentLevel = MutableLiveData<Int>()
    private val gameState = MutableLiveData<GameState>()

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)

        every { viewModel.currentLevel } returns currentLevel
        every { viewModel.sequence } returns MutableLiveData()
        every { viewModel.gameState } returns gameState

        composeTestRule.setContent {
            GameScreen(viewModel = viewModel, navHostController = mockk(relaxed = true))
        }
    }

    @Test
    fun testGameButtonRendersAsClickable() {
        currentLevel.postValue(1)
        gameState.postValue(GameState.GAME_TAP)

        composeTestRule.onNodeWithTag("game-button-1").assertExists().assertHasClickAction()
    }
}