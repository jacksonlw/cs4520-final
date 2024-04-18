package com.cs4520.brainflex.view.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.Duration
import java.time.Instant

@Composable
fun LeaderboardScreen(viewModel: LeaderboardViewModel, navHostController: NavHostController) {
    val scores by viewModel.scores.observeAsState(listOf())
    val state by viewModel.state.observeAsState(LeaderboardState.LOADING)

    LaunchedEffect(true) {
        viewModel.loadNextPage()
    }

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxHeight()) {
        Column {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
                Text(
                    text = "Leaderboard",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary
                )
            }
            LeaderboardHeader()
            if (scores.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                ) {
                    if (state == LeaderboardState.LOADING) {
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    }
                    if (state == LeaderboardState.ERROR) {
                        Text(text = "Error loading leaderboard scores")
                    }
                    if (state == LeaderboardState.SUCCESS) {
                        Text(text = "No scores")
                    }
                }
            }
            LazyColumn {
                itemsIndexed(scores) { index, score ->
                    val isOdd = index % 2 == 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = (index + 1).toString(), modifier = Modifier
                                .weight(0.5f)
                                .padding(4.dp), color = MaterialTheme.colors.primary
                        )
                        Text(
                            text = score.username, modifier = Modifier
                                .weight(1f)
                                .padding(4.dp), color = MaterialTheme.colors.primary
                        )
                        Text(
                            text = score.score.toString(), modifier = Modifier
                                .weight(0.5f)
                                .padding(4.dp), color = MaterialTheme.colors.primary
                        )
                        Text(
                            text = formatTimeAgo(score.insertedAt), modifier = Modifier
                                .weight(1f)
                                .padding(4.dp), color = MaterialTheme.colors.primary
                        )
                    }
                    if (index >= scores.size - 1) {
                        viewModel.loadNextPage()
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaderboardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colors.onBackground)
    ) {
        Text(
            text = "#", modifier = Modifier
                .weight(0.5f)
                .padding(4.dp), color = MaterialTheme.colors.primary
        )
        Text(
            text = "User", modifier = Modifier
                .weight(1f)
                .padding(4.dp), color = MaterialTheme.colors.primary
        )
        Text(
            text = "Score", modifier = Modifier
                .weight(0.5f)
                .padding(4.dp), color = MaterialTheme.colors.primary
        )
        Text(
            text = "When", modifier = Modifier
                .weight(1f)
                .padding(4.dp), color = MaterialTheme.colors.primary
        )
    }
}

private fun formatTimeAgo(utcTime: String): String {
    val parsedTime = Instant.parse(utcTime)
    val now = Instant.now()

    val duration = Duration.between(parsedTime, now)

    return when {
        duration.toDays() >= 1 -> "${duration.toDays()} days ago"
        duration.toHours() >= 1 -> "${duration.toHours()} hours ago"
        duration.toMinutes() >= 1 -> "${duration.toMinutes()} minutes ago"
        else -> "a few seconds ago"
    }
}