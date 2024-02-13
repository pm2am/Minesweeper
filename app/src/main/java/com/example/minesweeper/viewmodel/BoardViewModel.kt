package com.example.minesweeper.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minesweeper.data.Cell
import com.example.minesweeper.data.GameEntity
import com.example.minesweeper.room.GameDao
import com.example.minesweeper.utils.CellBFS
import com.example.minesweeper.utils.CellSerializer
import com.example.minesweeper.utils.generateBoard
import kotlinx.coroutines.launch

class BoardViewModel(private val gameDao: GameDao): ViewModel() {
    private val size = 8
    private val minesCount = 10
    var cells : List<List<Cell>> by mutableStateOf(generateBoard(size, minesCount))
        private set

    var revealedCount = mutableIntStateOf(size*size)
        private set

    var timer = mutableIntStateOf(0)
        private set

    var timerKey = mutableIntStateOf(0)
        private set

    fun resumeGame() {
        viewModelScope.launch {
            val listOfGames = gameDao.getGames()
            if (listOfGames.isNotEmpty()) {
                val saveGame = listOfGames[0]
                val saveCells = CellSerializer.deserialize(saveGame.cells)
                revealedCount.intValue = saveGame.revealedCount
                timer.intValue = saveGame.timer
            }
        }
    }

    fun resetBoard() {
        viewModelScope.launch {
            gameDao.deleteRecord()
        }
        cells = generateBoard(size = size, minesCount = minesCount)
        revealedCount.intValue = size * size
        timer.intValue = 0
        timerKey.intValue++
    }

    fun saveGame() {
        viewModelScope.launch {
            gameDao.deleteRecord()
            val serializedCell = CellSerializer.serialize(cells)
            val entity = GameEntity(
                cells = serializedCell,
                revealedCount = revealedCount.intValue,
                timer = timer.intValue
            )
            gameDao.insertGame(entity)
        }
    }

    fun onCellClicked(rowIndex: Int, colIndex: Int) {
        if (cells[rowIndex][colIndex].isRevealed || revealedCount.intValue==-1 || revealedCount.intValue==10) {
            return
        }
        if (cells[rowIndex][colIndex].isMined) {
            revealedCount.intValue = -1
            cells[rowIndex][colIndex].isRevealed = true
        } else if (cells[rowIndex][colIndex].minesAround==0) {
            revealedCount.intValue -= CellBFS(rowIndex, colIndex, data = cells)
        } else {
            cells[rowIndex][colIndex].isRevealed = true
            revealedCount.intValue--
        }
    }
}