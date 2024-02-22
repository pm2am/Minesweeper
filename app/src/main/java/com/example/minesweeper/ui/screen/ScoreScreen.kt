package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.minesweeper.viewmodel.BoardViewModel

@Composable
fun ScoreScreen() {
    val viewModel = LocalView.current.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<BoardViewModel>(it)
    }
    Column {
        Text(text ="Score Screen ${viewModel?.scoreState?.value?.winCount}/${viewModel?.scoreState?.value?.lossCount}")
    }
}
