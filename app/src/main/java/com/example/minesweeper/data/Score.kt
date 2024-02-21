package com.example.minesweeper.data

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class Score(
    winCount: Int,
    lossCount: Int
) {
    var winCount by mutableIntStateOf(winCount)
    var lossCount by mutableIntStateOf(lossCount)
}