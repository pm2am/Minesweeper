package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.minesweeper.viewmodel.BoardViewModel

@Composable
fun MainScreen(
    onBoardClicked: () -> Unit = {},
    onScoreClicked: () -> Unit = {}
) {
    val viewModel = LocalView.current.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<BoardViewModel>(it)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (viewModel?.shouldShowResume?.value == true) {
            ElevatedButton(onClick = onBoardClicked,
            ) {
                Text(
                    text = "Resume",
                )
            }
        }

        ElevatedButton(
            onClick = {
                viewModel?.resetBoard()
                onBoardClicked()
            },
        ) {
            Text(
                text = "Board",
                )
        }

        ElevatedButton(
            onClick = {
                viewModel?.updateScoreState()
                onScoreClicked()
            }
        ) {
            Text(
                text = "Score",
            )
        }
    }
}
