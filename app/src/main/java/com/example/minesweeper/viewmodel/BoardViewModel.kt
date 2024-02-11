package com.example.minesweeper.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.minesweeper.utils.generateBoard

class BoardViewModel : ViewModel() {
    var cells by mutableStateOf(generateBoard(8, 10))
        private set

    var revealedCount = mutableIntStateOf(8*8)
        private set

    var timer = mutableIntStateOf(0)
        private set

    var timerKey = mutableIntStateOf(0)
        private set

    fun updateBoard() {
        cells = generateBoard(8, 10)
        revealedCount.intValue = 8*8
        timer.intValue = 0
        timerKey.intValue++
    }
}