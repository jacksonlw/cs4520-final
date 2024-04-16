package com.cs4520.brainflex.view

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InformationScreen(
    navController: NavController
){
    Surface ( color = MaterialTheme.colors.secondary ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal= 20.dp, vertical = 10.dp)
        ){
            Title()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ){
                HowToPlay()
                MemorySequenceGame()
                Spacer(modifier = Modifier.height(20.dp))
                AdditionalInfo()
            }
        }
    }
}

@Composable
fun Title(){
    Text(text = "Sequence Memory Test",
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(10.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.subtitle1)
}

@Composable
fun HowToPlay(){
    Column(){
        Text(text = "How To Play", style = MaterialTheme.typography.caption)
        Text(text = "1. Memorize the sequence of buttons that light up\n" +
                "2. Press the buttons in order\n\n" +
                "The sequence gets longer as you complete each pattern. If you fail to complete the pattern, the test will fail.\n",
            style = MaterialTheme.typography.caption, modifier= Modifier.padding(0.dp))
    }
}

@Composable
fun BlinkingCard() {
    val blue = MaterialTheme.colors.background
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
        modifier = Modifier.size(40.dp).padding(5.dp), backgroundColor = color.value
    ){}
}

@Composable
fun NormalCard() {
    Card( modifier = Modifier.size(40.dp).padding(5.dp), backgroundColor = (MaterialTheme.colors.background)){}
}

@Composable
fun MemorySequenceGame() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Observe then Tap:", color=MaterialTheme.colors.primary,
            fontSize = 12.sp, textAlign = TextAlign.Center)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(120.dp).width(120.dp)
        ) {
            items(9) { index ->
                if(index==4){
                    BlinkingCard()
                } else {
                    NormalCard()
                }
            }
        }
    }
}


@Composable
fun AdditionalInfo(){
    Column(){
        Text(text = "What Does the Test Measure?\nThe Sequence Memory Test measures working memory capacity along with cognitive processing speed and attention. The average score for beginners are around 0-32 while the experts score range from 32-160.",
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(10.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.caption)
    }
}

@Preview(showBackground = true)
@Composable
fun InformationScreenPreview() {
}