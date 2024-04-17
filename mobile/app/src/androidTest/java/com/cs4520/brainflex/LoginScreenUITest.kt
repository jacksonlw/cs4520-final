package com.cs4520.brainflex

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.brainflex.view.login.LogInScreen
import com.cs4520.brainflex.view.login.LogInViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginScreenUITest {
    private lateinit var viewModel: LogInViewModel

    private val recentUsernamesData = MutableLiveData<List<String>>(listOf())

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)

        every { viewModel.recentUsernames } returns recentUsernamesData
        every { viewModel.loginResponseEvent } returns MutableSharedFlow<Boolean>(
            extraBufferCapacity = 1
        ).asSharedFlow()

        composeTestRule.setContent {
            LogInScreen(viewModel = viewModel, navHostController = rememberNavController())
        }
    }

    @Test
    fun testRecentUsernamesShowing() {
        recentUsernamesData.postValue(listOf("test-user"))
        composeTestRule.onNodeWithText("test-user")
            .assertExists("recent users must appear on login screen")
    }

    @Test
    fun testRecentUsernamesHides() {
        recentUsernamesData.postValue(listOf())
        composeTestRule.onNodeWithText("Recent Logins")
            .assertDoesNotExist()
    }
}