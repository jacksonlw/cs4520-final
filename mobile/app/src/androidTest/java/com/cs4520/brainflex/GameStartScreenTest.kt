package com.cs4520.brainflex

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.brainflex.view.GameStartScreen
import com.cs4520.brainflex.view.login.LogInScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameStartScreenTest {
    @get: Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testGameOverAndScoreShows() {
        composeTestRule.setContent {
            GameStartScreen(score = 10, navHostController = rememberNavController())
        }

        composeTestRule.onNodeWithText(text = "game over", substring = true, ignoreCase = true).assertExists("'game over' text must show on the screen")
        composeTestRule.onNodeWithText(text = "10", substring = true).assertExists("score must show on screen")
    }


    @Test
    fun testGameOverHiddenWithNullScore() {
        composeTestRule.setContent {
            GameStartScreen(score = null, navHostController = rememberNavController())
        }

        composeTestRule.onNodeWithText(text = "game over", substring = true, ignoreCase = true).assertDoesNotExist()
    }
}