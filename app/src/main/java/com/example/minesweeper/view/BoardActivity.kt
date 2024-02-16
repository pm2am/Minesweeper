package com.example.minesweeper.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minesweeper.data.Cell
import com.example.minesweeper.ui.theme.MinesweeperTheme
import com.example.minesweeper.utils.LogComposition
import com.example.minesweeper.utils.TAG
import com.example.minesweeper.viewmodel.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class BoardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: BoardViewModel = hiltViewModel()
            MinesweeperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Board(viewModel)
                }
            }
        }
    }
}

@Composable
fun Board(viewModel: BoardViewModel) {
    LogComposition(tag = TAG, msg = "Board")
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LogComposition(tag = TAG, msg = "Column")
        GridWithColors(viewModel)
        InfoLayout(viewModel)
    }
}

@Composable
fun InfoLayout(
    viewModel: BoardViewModel
) {
    val timer = viewModel.timer
    val timerKey = viewModel.timerKey

    LaunchedEffect(key1 = timerKey.intValue) {
        while (viewModel.revealedCount.intValue!=-1 && viewModel.revealedCount.intValue!=10) {
            timer.intValue++
            delay(1.seconds)
        }
    }

    Text(
        text = "${timer.intValue}"
    )
    Row {
        ElevatedButton(onClick = {
            viewModel.resumeGame()
        }) {
            Text(text = "RESUME")
        }
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
            .padding(8.dp)
    )
}

@Composable
fun GridWithColors(viewModel: BoardViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1)
    ) {
        items(viewModel.cells.size) { rowIndex ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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
