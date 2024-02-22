package com.example.minesweeper.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.minesweeper.viewmodel.BoardViewModel

@Composable
fun ScoreScreen() {
    val viewModel = LocalView.current.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<BoardViewModel>(it)
    }
    LaunchedEffect(key1 = Unit) {
        viewModel?.updateScoreState()
    }

    Column(
        Modifier.fillMaxWidth()
    ) {
        Text(text = "Score List")
        Row(
            Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "DATE")
            Text(text = "WIN")
            Text(text = "LOSS")
        }

        viewModel?.scores?.forEach {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = it.date)
                Text(text = "${it.winCount}")
                Text(text = "${it.lossCount}")
            }
        }

    }
}
