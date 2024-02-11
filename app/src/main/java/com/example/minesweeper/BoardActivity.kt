package com.example.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minesweeper.data.Cell
import com.example.minesweeper.ui.theme.MinesweeperTheme
import com.example.minesweeper.utils.CellBFS
import com.example.minesweeper.utils.LogComposition
import com.example.minesweeper.utils.TAG
import com.example.minesweeper.utils.generateBoard
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class BoardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Board()
                }
            }
        }
    }
}

@Composable
fun Board() {
    val cells = remember {
        mutableStateOf(generateBoard(8, 10))
    }
    val revealedCount = remember {
        mutableIntStateOf(8*8)
    }
    Column {
        GridWithColors(data = cells.value, revealedCount = revealedCount)
        InfoLayout(cells = cells, revealedCount = revealedCount)
    }
}

@Composable
fun InfoLayout(
    cells: MutableState<Array<Array<Cell>>>,
    revealedCount: MutableIntState
) {
    val timer = remember {
        mutableIntStateOf(0)
    }
    val timerKey = remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = timerKey.intValue) {
        while (revealedCount.intValue!=-1 && revealedCount.intValue!=10) {
            timer.intValue++
            delay(1.seconds)
        }
    }
    Text(
        text = "${timer.intValue}"
    )
    ElevatedButton(onClick = {
        cells.value = generateBoard(8, 10)
        revealedCount.intValue = 8*8
        timer.intValue = 0
        timerKey.intValue++
    }) {
        Text(text = "RESET")
    }
    Text(
        text = when (revealedCount.intValue) {
            -1 -> "LOSE"
            10 -> "WIN"
            else -> "Count: ${revealedCount.intValue}"
        },
        Modifier
            .background(
                color = when (revealedCount.intValue) {
                    -1 -> Color.Red
                    10 -> Color.Green
                    else -> Color.Cyan
                }
            )
            .padding(8.dp)
    )
}

@Composable
fun GridWithColors(data: Array<Array<Cell>>, revealedCount: MutableIntState) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        items(data.size) { rowIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                LogComposition(tag = TAG, msg = "Row")
                for (colIndex in data[rowIndex].indices) {
                    CellScope(rowIndex = rowIndex, colIndex = colIndex, data = data, revealedCount)
                }
            }
        }
    }
}

@Composable
fun CellScope(
    rowIndex: Int,
    colIndex: Int,
    data: Array<Array<Cell>>,
    revealedCount: MutableIntState
) {
    val curCell = data[rowIndex][colIndex]
    CellElement(cell = curCell, onCellClicked = {
        if (curCell.isRevealed || revealedCount.intValue==-1 || revealedCount.intValue==10) {
            return@CellElement
        }
        if (data[rowIndex][colIndex].isMined) {
            revealedCount.intValue = -1
            data[rowIndex][colIndex].isRevealed = true
        } else if (data[rowIndex][colIndex].minesAround==0) {
            revealedCount.intValue -= CellBFS(rowIndex, colIndex, data = data)
        } else {
            data[rowIndex][colIndex].isRevealed = true
            revealedCount.intValue--
        }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BoardPreview() {
    MinesweeperTheme {
        Board()
    }
}
