package com.example.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.minesweeper.utils.LogComposition
import com.example.minesweeper.utils.TAG
import com.example.minesweeper.utils.generateBoard

class MainActivity : ComponentActivity() {
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
        generateBoard(8, 10)
    }
    GridWithColors(data = cells)
}

@Composable
fun GridWithColors(data: Array<Array<Cell>>) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        items(data.size) { rowIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                LogComposition(tag = TAG, msg = "Row")
                for (colIndex in data[rowIndex].indices) {
                    CellScope(rowIndex = rowIndex, colIndex = colIndex, data = data)
                }
            }
        }
    }
}

@Composable
fun CellScope(rowIndex: Int, colIndex: Int, data: Array<Array<Cell>>) {
    val neighboursDirection = arrayOf(
        arrayOf(-1,0),
        arrayOf(1,0),
        arrayOf(0,-1),
        arrayOf(0,1)
    )
    CellElement(cell = data[rowIndex][colIndex], onColorChange = {
        data[rowIndex][colIndex].color = it
        data[rowIndex][colIndex].isRevealed = true
        for ((dx, dy) in neighboursDirection) {
            if (rowIndex+dx>=0 && rowIndex+dx<data.size && colIndex+dy>=0 && colIndex+dy<data.size) {
                data[rowIndex+dx][colIndex+dy].color = it
                data[rowIndex+dx][colIndex+dy].isRevealed = true
            }
        }
    })
}


@Composable
fun CellElement(cell: Cell, onColorChange: (Color) -> Unit) {
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
                if (!cell.isRevealed)
                    onColorChange(Color.Blue)
            }
        ) {
            if (cell.isRevealed) {
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