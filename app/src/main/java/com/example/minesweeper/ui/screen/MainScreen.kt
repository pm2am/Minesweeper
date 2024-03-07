package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.minesweeper.R
import com.example.minesweeper.viewmodel.BoardViewModel

@Composable
fun MainScreen(
    onBoardClicked: () -> Unit = {},
    onScoreClicked: () -> Unit = {}
) {
    val viewModel = LocalView.current.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<BoardViewModel>(it)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel?.resumeOrNotGame()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (viewModel?.shouldShowResume?.value == true) {
            ElevatedButton(onClick = onBoardClicked,
            ) {
                Text(
                    text = stringResource(id = R.string.menu_resume),
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
                text = stringResource(id = R.string.menu_board),
                )
        }

        ElevatedButton(
            onClick = {
                onScoreClicked()
            }
        ) {
            Text(
                text = stringResource(id = R.string.menu_score),
            )
        }
    }
}
