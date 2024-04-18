package com.cs4520.brainflex

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.brainflex.view.InformationScreen
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InformationScreenTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHowToPlayDisplays() {
        composeTestRule.setContent {
            InformationScreen(navController = mockk(relaxed = true))
        }

        composeTestRule.onNodeWithText("how to play", substring = true, ignoreCase = true)
            .assertExists("'how to play' must be shown on the information screen")
    }
}