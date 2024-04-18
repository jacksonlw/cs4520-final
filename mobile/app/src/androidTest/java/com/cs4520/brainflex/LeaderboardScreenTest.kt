package com.cs4520.brainflex

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.brainflex.dto.Score
import com.cs4520.brainflex.view.leaderboard.LeaderboardScreen
import com.cs4520.brainflex.view.leaderboard.LeaderboardState
import com.cs4520.brainflex.view.leaderboard.LeaderboardViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant


@RunWith(AndroidJUnit4::class)
class LeaderboardScreenTest {
    private lateinit var viewModel: LeaderboardViewModel

    private val scores = MutableLiveData<List<Score>>(listOf())
    private val state = MutableLiveData<LeaderboardState>()

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)

        every { viewModel.scores } returns scores
        every { viewModel.state } returns state

        composeTestRule.setContent {
            LeaderboardScreen(viewModel = viewModel, navHostController = mockk(relaxed = true))
        }
    }

    @Test
    fun testSuccessStateWithNoScores() {
        scores.postValue(listOf())
        state.postValue(LeaderboardState.SUCCESS)

        composeTestRule.onNodeWithText("No scores")
            .assertExists("'no scores' message must appear when scores is empty")
    }

    @Test
    fun testSuccessStateWithScores() {
        scores.postValue(
            listOf(
                Score(
                    1,
                    "user",
                    10,
                    Instant.now().toString()
                )
            )
        )
        state.postValue(LeaderboardState.SUCCESS)

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("user")
            .assertExists("leaderboard must show username")

        composeTestRule.onNodeWithText("10")
            .assertExists("leaderboard must show score")

        composeTestRule.onNodeWithText("1")
            .assertExists("leaderboard must show ranking(#)")

        composeTestRule.onNodeWithText("a few seconds ago")
            .assertExists("leaderboard must when it was logged")
    }
}