import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepo
import com.cs4520.brainflex.view.game.GameState
import com.cs4520.brainflex.view.game.GameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify


class GameViewModelTest {

    // Executes each task synchronously using Architecture Components
    @JvmField
    @Rule
    public val instantExecutor = InstantTaskExecutorRule()

    private lateinit var gameViewModel: GameViewModel
    private lateinit var currentLevelObserver: Observer<Int>
    private lateinit var sequenceObserver: Observer<List<Int>>
    private lateinit var gameStateObserver: Observer<GameState>
    private lateinit var apiClient: ApiClient
    private lateinit var userRepo: UserRepo
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        apiClient = mock(ApiClient::class.java)
        userRepo = mock(UserRepo::class.java)
        gameViewModel = GameViewModel(apiClient, userRepo)
        currentLevelObserver = Mockito.mock(Observer::class.java) as Observer<Int>
        sequenceObserver = Mockito.mock(Observer::class.java) as Observer<List<Int>>
        gameStateObserver = Mockito.mock(Observer::class.java) as Observer<GameState>

        gameViewModel.currentLevel.observeForever(currentLevelObserver)
        gameViewModel.sequence.observeForever(sequenceObserver)
        gameViewModel.gameState.observeForever(gameStateObserver)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Reset the main dispatcher after testing
        Dispatchers.resetMain()
        // Cleanup the main thread surrogate
        mainThreadSurrogate.close()
    }

    @Test
    fun startNewGame_UpdatesLiveData() {
        val level = 1
        val captor = ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<List<Int>>


        // Call the function to be tested
        gameViewModel.startNewGame(level)
        // Verify that the LiveData objects are updated with the expected values
        verify(currentLevelObserver).onChanged(level)
        verify(sequenceObserver).onChanged(captor.capture())
        verify(gameStateObserver).onChanged(GameState.GAME_OBSERVE)

        assertEquals(captor.value.size, 1)
    }

    @Test
    fun nextStep_Correct_FirstDigit() {
        val navHostController = mock(NavHostController::class.java)
        val captor1 = ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<Int>
        val captor2 = ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<List<Int>>
        gameViewModel.startNewGame(2)
        val cellNumber1 = gameViewModel.sequence.value?.get(0)

        // Call the function to be tested
        if (cellNumber1 != null) {
            gameViewModel.nextStep(cellNumber1, navHostController = navHostController)
        }
        // Verify that the LiveData objects are updated with the expected values
        verify(currentLevelObserver).onChanged(captor1.capture())
        verify(sequenceObserver).onChanged(captor2.capture())
        verify(gameStateObserver).onChanged(GameState.GAME_OBSERVE)
        assertEquals(captor1.value, 2)
        assertEquals(captor2.value.size, 2)
    }

    @Test
    fun nextStep_Correct_LastDigit() {
        val navHostController = mock(NavHostController::class.java)

        // Setup ArgumentCaptor for currentLevel
        val captor1 = ArgumentCaptor.forClass(Int::class.java)
        // Setup ArgumentCaptor for sequence
        val captor2 = ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<List<Int>>

        // Call the function to be tested
        gameViewModel.startNewGame(2)

        // Call the nextStep method for the first time
        val cellNumber1 = gameViewModel.sequence.value?.get(0)
        if (cellNumber1 != null) {
            gameViewModel.nextStep(cellNumber1, navHostController = navHostController)
        }

        // Verify the changes after the first invocation
        verify(currentLevelObserver, times(1)).onChanged(captor1.capture())
        verify(sequenceObserver, times(1)).onChanged(captor2.capture())
        verify(gameStateObserver, times(1)).onChanged(GameState.GAME_OBSERVE)

        // Reset the captors for subsequent invocations
        captor1.allValues.clear()
        captor2.allValues.clear()

        // Call the nextStep method for the second time
        val cellNumber2 = gameViewModel.sequence.value?.get(1)
        if (cellNumber2 != null) {
            gameViewModel.nextStep(cellNumber2, navHostController = navHostController)
        }

        // Verify the changes after the second invocation
        verify(currentLevelObserver, times(2)).onChanged(captor1.capture())
        verify(sequenceObserver, times(2)).onChanged(captor2.capture())
        verify(gameStateObserver, times(2)).onChanged(GameState.GAME_OBSERVE)

        // Assert the values captured after the second invocation
        assertEquals(3, captor1.value)
        assertEquals(3, captor2.value.size)
    }
}
