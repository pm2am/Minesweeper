package com.example.minesweeper.data

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class Cell(
    isMined: Boolean = false,
    isRevealed: Boolean = false,
    minesAround: Int = 0
) {
    var isMined by mutableStateOf(isMined)
    var isRevealed by mutableStateOf(isRevealed)
    var minesAround by mutableIntStateOf(minesAround)
}