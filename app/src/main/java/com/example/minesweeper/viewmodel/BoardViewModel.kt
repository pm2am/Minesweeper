package com.example.minesweeper.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minesweeper.data.Cell
import com.example.minesweeper.data.Score
import com.example.minesweeper.room.GameEntity
import com.example.minesweeper.room.GameDao
import com.example.minesweeper.room.ScoreDao
import com.example.minesweeper.room.ScoreEntity
import com.example.minesweeper.utils.CellBFS
import com.example.minesweeper.utils.CellSerializer
import com.example.minesweeper.utils.generateBoard
import com.example.minesweeper.utils.initializeBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val gameDao: GameDao,
    private val scoreDao: ScoreDao
): ViewModel() {
    private val size = 9
    private val minesCount = 10
    var cells : List<List<Cell>> by mutableStateOf(initializeBoard(size))
        private set

    var revealedCount = mutableIntStateOf(size*size)
        private set

    var timer = mutableIntStateOf(0)
        private set

    var timerKey = mutableIntStateOf(0)
        private set

    var shouldShowResume = mutableStateOf(false)

    var scoreState = mutableStateOf(Score(0,0))

    fun resumeOrNotGame() {
        viewModelScope.launch {
            val listOfGames = gameDao.getGames()
            if (listOfGames.isNotEmpty()) {
                val saveGame = listOfGames[0]
                val saveCells: List<List<Cell>> = CellSerializer.deserialize(saveGame.cells)
                cells = saveCells
                revealedCount.intValue = saveGame.revealedCount
                timer.intValue = saveGame.timer
                shouldShowResume.value = true
            } else {
                shouldShowResume.value = false
            }
        }
    }

    fun resetBoard() {
        viewModelScope.launch {
            gameDao.deleteRecord()
        }
        cells = initializeBoard(size = size)
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
        if (revealedCount.intValue == size*size) {
            generateBoard(cells, minesCount, rowIndex, colIndex)
        }
        if (cells[rowIndex][colIndex].isRevealed || revealedCount.intValue==-1 || revealedCount.intValue==minesCount) {
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

    fun addDefaultScore() {
        viewModelScope.launch {
            scoreDao.insertScore(
                id = 1L,
                1,
                1
            )

            val entity = scoreDao.getScore(1L)
            scoreState.value.winCount = entity.winCount
            scoreState.value.lossCount = entity.lossCount
        }
    }

}
