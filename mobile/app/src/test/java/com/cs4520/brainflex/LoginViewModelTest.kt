package com.cs4520.brainflex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserEntity
import com.cs4520.brainflex.dao.UserRepo
import com.cs4520.brainflex.view.login.LogInViewModel
import com.cs4520.brainflex.workmanager.LogInWorkManager
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.time.LocalDateTime


class LoginViewModelTest {

    @JvmField
    @Rule
    public val instantExecutor = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LogInViewModel

    private lateinit var recentUsernamesObserver: Observer<List<String>>

    private val recentUsers = MutableLiveData<List<UserEntity>>(listOf())
    private lateinit var apiClient: ApiClient
    private lateinit var userRepo: UserRepo

    private val user1 = UserEntity(
        username = "test",
        insertedAt = LocalDateTime.now()
    )

    @Before
    fun setup() {
        apiClient = mock(ApiClient::class.java)
        userRepo = mock(UserRepo::class.java)

        `when`(userRepo.recent).thenReturn(recentUsers)

        val wm = mock(WorkManager::class.java)
        val loginWm = LogInWorkManager(wm)

        loginViewModel = LogInViewModel(apiClient, userRepo, loginWm)

        recentUsernamesObserver = mock(Observer::class.java) as Observer<List<String>>
        loginViewModel.recentUsernames.observeForever(recentUsernamesObserver)
    }

    @Test
    fun testRecentUsersTransformsToUsernames() {
        recentUsers.value = listOf(user1)

        val usernames = loginViewModel.recentUsernames.value
        assertThat("usernames must not be null", usernames, `is`(notNullValue()))
        assertThat("usernames must have one item", usernames?.size, `is`(equalTo(1)))
        assertThat("first username in usernames must be 'test'", usernames?.get(0), `is`(equalTo("test")))
    }
}