package com.cs4520.brainflex.view

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cs4520.brainflex.ui.theme.BrainFlexTheme

@Composable
fun InformationScreen(
    navController: NavController
){
    Surface ( color = MaterialTheme.colors.secondary ) {
        Column (
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ){
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
        style = MaterialTheme.typography.h5)
}

@Composable
fun HowToPlay(){
    Column(){
        Text(text = "How To Play", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "1. Memorize the sequence of buttons that light up\n" +
                "2. Press the buttons in order\n\n" +
                "The sequence gets longer as you complete each pattern \n" +
                "\n" +
                "If you fail to complete the pattern, the test will fail.\n",
            style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun GameDemo() {

}
@Composable
fun AdditionalInfo(){
    Column(){

        Text(text = "What Does the Test Measure?",
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(10.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.h6)

        Text(text = "The Sequence Memory Test measures working memory capacity along with cognitive processing speed and attention.\n\n" +
                "The average score for beginners are around 0-32 while the experts score range from 32-160.\n",
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(10.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.subtitle1)
    }
}

@Preview(showBackground = true)
@Composable
fun InformationScreenPreview() {
    BrainFlexTheme {
        Surface ( color = MaterialTheme.colors.secondary ) {
            Column (
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ){
                Title()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ){
                    HowToPlay()
                    GameDemo()
                    AdditionalInfo()
                }
            }
        }
    }
}