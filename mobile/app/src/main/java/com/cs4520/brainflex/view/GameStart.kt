package com.cs4520.brainflex.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cs4520.brainflex.Screen

@Composable
fun GameStartScreen(
    score: Int?,
    navHostController: NavHostController,
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            if (score != null) {
                Text(
                    text = "Game Over. You finished with a score of $score.",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            LinkBtn(text = "START", onClick = {
                navHostController.navigate(Screen.GAME.name)
            })
            LinkBtn(text = "LEADERBOARD", onClick = {
                navHostController.navigate(Screen.LEADERBOARD.name)
            })
            LinkBtn(text = "RULES", onClick = {
                navHostController.navigate(Screen.INFORMATION.name)
            })
            LinkBtn(text = "LOG OUT", onClick = {
                navHostController.navigate(Screen.LOGIN.name)
            })
        }
    }
}


@Composable
fun LinkBtn(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(5.dp),
    ) {
        Text(text, color = Color.Black)
    }
}

@Preview
@Composable
fun GameStartPreview() {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text("START", color = Color.Black)
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(5.dp),
            ) {
                Text("RULES", color = Color.Black)
            }
        }
    }
}