import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cs4520.brainflex.api.ApiClient
import com.cs4520.brainflex.dao.UserRepo
import com.cs4520.brainflex.dao.UserRepository
import com.cs4520.brainflex.view.game.GameState
import com.cs4520.brainflex.view.game.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.mock
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
}
