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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minesweeper.ui.theme.MinesweeperTheme

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

                }
            }
        }
    }
}

@Composable
fun Board() {
    val colors = remember {
        mutableStateOf(
            Array(8) {
                Array(8) {
                    Color.Red
                }
            }
        )
    }

    GridWithColors(data = colors)
}

@Composable
fun GridWithColors(data: MutableState<Array<Array<Color>>>) {
    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
        items(data.value.size) { rowIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                for (colIndex in data.value[rowIndex].indices) {
                    Cell(color = data.value[rowIndex][colIndex], onColorChange = {
                        data.value[rowIndex][colIndex] = it
                    })
                }
            }
        }
    }
}

@Composable
fun Cell(color: Color, onColorChange: (Color) -> Unit) {
    Surface(
        modifier = Modifier
            .size(40.dp)
            .padding(4.dp),
        color = color
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onColorChange(Color.Blue)
            })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BoardPreview() {
    MinesweeperTheme {
        Board()
    }
}