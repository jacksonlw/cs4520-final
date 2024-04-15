package com.cs4520.brainflex

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.AnimationResult
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.isFinished
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen(viewModel: GameViewModel, navHostController: NavHostController) {

    val currentLevel by viewModel.currentLevel.observeAsState(1)
    val sequence by viewModel.sequence.observeAsState(listOf())
    val gameState by viewModel.gameState.observeAsState(GameState.IN_PROGRESS)

    Surface( color = MaterialTheme.colors.background) {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)){
                Level(gameState, currentLevel)
                Spacer(modifier = Modifier.height(20.dp))
                Log.d("SEQUENCE HERE2", sequence.toString())
                MemorySequenceGame(viewModel, sequence, gameState, navHostController)
            }

    }
}
@Composable
fun Level(gameState: GameState, currentScore: Int) { 
    Text("LEVEL " + currentScore, color = MaterialTheme.colors.primary, fontSize = 20.sp) 
}


@Composable
fun MemorySequenceGame(
    viewModel: GameViewModel,
    sequence: List<Int>,
    gameState: GameState,
    navHostController: NavHostController,
) {
    val blue = MaterialTheme.colors.onBackground
    val flash = MaterialTheme.colors.primary
    var isDone by remember { mutableStateOf(false) }
    val flashAnimationSpec = keyframes<Color> {
        durationMillis = 1000
        blue at 0  // Initial color
        flash at 500
        blue at 1000 // Full white color at 500ms
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            "Observe", color = MaterialTheme.colors.primary,
            fontSize = 12.sp, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))

        val animatables = remember {
            List(9) {
                Animatable(blue)
            }
        }
// Start sequential animation
        LaunchedEffect(Unit) {
            // Iterate through each index and animate sequentially
            intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8).forEachIndexed { index, item ->
               Log.d("HDHDHDHDHDH", "HDHD")
                // Animate the current item
                val animation = animatables[0].animateTo(flash, flashAnimationSpec)
                Log.d("HDHDHDHDHDH", animation.endState.isFinished.toString())
                if (index == 8) {
                    Log.d("TRUE", index.toString())
                    isDone = true
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
        ) {

            items(9) { index ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(5.dp)
                        .background(animatables[index].value)
                        .clickable(enabled = isDone) {
                            viewModel.nextStep(index)
                            if (gameState == GameState.GAME_OVER) {
                                viewModel.resetGame()
                                navHostController.navigate(Screen.INFORMATION.name)
                            }
                        }
                ) {}
            }
        }
    }
}

