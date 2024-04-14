package com.cs4520.brainflex

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun GameScreen(viewModel: GameViewModel, navHostController: NavHostController,) {
    // Score = level
    val currentScore by viewModel.currentScore.observeAsState(0)
    val sequence by viewModel.sequence.observeAsState(listOf<Int>())
    val gameState by viewModel.gameState.observeAsState(GameState.IN_PROGRESS)

    // TODO: Change this to leaderboard
    if (gameState == GameState.GAME_OVER) {
        navHostController.navigate(Screen.LOGIN.name)
    }
    Surface( color = MaterialTheme.colors.background) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)){
            Level(gameState, currentScore)
            Spacer(modifier = Modifier.height(20.dp))
            MemorySequenceGame(viewModel, sequence)
        }
    }
}
@Composable
fun Level(gameState: GameState, currentScore: Int) { 
    Text("LEVEL " + currentScore, color = MaterialTheme.colors.primary, fontSize = 20.sp) 
}
@Composable
fun BlinkingCard(viewModel: GameViewModel, index: Int) {
    val blue = MaterialTheme.colors.onBackground
    val color = remember { Animatable(blue) }

    LaunchedEffect(Unit) {
        color.animateTo(
            targetValue = Color.White,
            animationSpec = tween(durationMillis = 3000)
        )
        color.animateTo(
            targetValue = blue,
            animationSpec = tween(durationMillis = 3000)
        )
    }
    Card(
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)
            .background(color.value)
            .clickable{ viewModel.nextStep(index) }
    ){}
}

@Composable
fun NormalCard(viewModel: GameViewModel, index: Int) {
    Card( modifier = Modifier
        .size(80.dp)
        .padding(5.dp)
        .background(MaterialTheme.colors.background)
        .clickable{ viewModel.nextStep(index) }){}
}

@Composable
fun MemorySequenceGame(viewModel: GameViewModel, data: List<Int>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Observe", color= MaterialTheme.colors.primary,
            fontSize = 12.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {
            items(9) { index ->
                if(data.contains(index)){
                    // the list of sequence is stored as indices of the card
                    // for instance, if the center cell of the board were to be the first sequence
                    // the  "data"  or list of sequence would contain 4
                    // considering that the board looks like this:
                    // 0 1 2
                    // 3 4 5
                    // 6 7 8
                    // SO if "data" contains it, the cell should blink:
                    BlinkingCard(viewModel, index)
                } else {
                    NormalCard(viewModel, index)
                }
            }
        }
    }
}
