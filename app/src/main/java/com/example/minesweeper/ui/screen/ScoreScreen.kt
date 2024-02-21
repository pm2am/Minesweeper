package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.minesweeper.viewmodel.BoardViewModel

@Composable
fun ScoreScreen(viewModel: BoardViewModel) {
    Column {
        Text(text ="Score Screen ${viewModel.scoreState.value.winCount}/${viewModel.scoreState.value.lossCount}")
    }
}
