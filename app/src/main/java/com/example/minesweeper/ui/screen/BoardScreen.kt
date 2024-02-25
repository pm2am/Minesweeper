package com.example.minesweeper.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.minesweeper.data.Cell
import com.example.minesweeper.utils.LogComposition
import com.example.minesweeper.utils.TAG
import com.example.minesweeper.viewmodel.BoardViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@Composable
fun BoardScreen() {
    val viewModel = LocalView.current.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<BoardViewModel>(it)
    }

    LogComposition(tag = TAG, msg = "Board")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogComposition(tag = TAG, msg = "Column")
        viewModel?.let {
            TopLayout(viewModel = it)
            GridWithColors(it)
            BottomLayout(it)
        }
    }
}

@Composable
fun TopLayout(viewModel: BoardViewModel) {
    val timer = viewModel.timer
    val timerKey = viewModel.timerKey

    LaunchedEffect(key1 = timerKey.intValue) {
        while (viewModel.revealedCount.intValue!=-1 && viewModel.revealedCount.intValue!=10) {
            timer.intValue++
            delay(1.seconds)
        }
    }

    Row (
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Time: ${timer.intValue} s"
        )

        Text(
            text = when (viewModel.revealedCount.intValue) {
                -1 -> "LOSE"
                10 -> "WIN"
                else -> "Count: ${viewModel.revealedCount.intValue}"
            },
            Modifier
                .background(
                    color = when (viewModel.revealedCount.intValue) {
                        -1 -> Color.Red
                        10 -> Color.Magenta
                        else -> Color.DarkGray
                    }
                )
        )
    }

}

@Composable
fun BottomLayout(
    viewModel: BoardViewModel
) {
    Row {
        ElevatedButton(onClick = {
            viewModel.resetBoard()
        }) {
            Text(text = "RESET")
        }
        ElevatedButton(onClick = {
            viewModel.saveGame()
        }) {
            Text(text = "SAVE")
        }
    }
}

@Composable
fun GridWithColors(viewModel: BoardViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1)
    ) {
        items(viewModel.cells.size) { rowIndex ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                LogComposition(tag = TAG, msg = "Row")
                for (colIndex in viewModel.cells[rowIndex].indices) {
                    CellScope(rowIndex = rowIndex, colIndex = colIndex, viewModel)
                }
            }
        }
    }
}

@Composable
fun CellScope(
    rowIndex: Int,
    colIndex: Int,
    viewModel: BoardViewModel
) {
    val curCell = viewModel.cells[rowIndex][colIndex]
    CellElement(cell = curCell, onCellClicked = {
        viewModel.onCellClicked(rowIndex, colIndex)
    })
}

@Composable
fun CellElement(cell: Cell, onCellClicked: () -> Unit) {
    LogComposition(tag = TAG, msg = "Cell")
    Surface(
        modifier = Modifier
            .size(40.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        color = if (cell.isRevealed)
            if (cell.isMined) Color.Red else Color.Blue
        else Color.Cyan
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onCellClicked()
            }
        ) {
            if (cell.isRevealed && !cell.isMined && cell.minesAround!=0) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "${cell.minesAround}",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}
