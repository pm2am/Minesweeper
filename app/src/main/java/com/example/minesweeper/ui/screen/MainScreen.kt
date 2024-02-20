package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.minesweeper.viewmodel.BoardViewModel

@Composable
fun MainScreen(
    viewModel: BoardViewModel,
    onBoardClicked: () -> Unit = {},
    onScoreClicked: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (viewModel.shouldShowResume.value) {
            ElevatedButton(onClick = onBoardClicked,
            ) {
                Text(
                    text = "Resume",
                )
            }
        }

        ElevatedButton(
            onClick = {
                viewModel.resetBoard()
                onBoardClicked()
            },
        ) {
            Text(
                text = "Board",
                )
        }

        ElevatedButton(onClick = onScoreClicked,
        ) {
            Text(
                text = "Score",
            )
        }
    }
}
