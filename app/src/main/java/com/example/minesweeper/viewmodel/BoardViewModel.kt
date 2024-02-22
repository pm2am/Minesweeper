package com.example.minesweeper.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minesweeper.data.Cell
import com.example.minesweeper.data.Score
import com.example.minesweeper.room.dao.GameDao
import com.example.minesweeper.room.dao.ScoreDao
import com.example.minesweeper.room.entity.GameEntity
import com.example.minesweeper.utils.CellBFS
import com.example.minesweeper.utils.CellSerializer
import com.example.minesweeper.utils.DateFormatter
import com.example.minesweeper.utils.generateBoard
import com.example.minesweeper.utils.initializeBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
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

    var scores = mutableStateListOf<Score>()

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
        if (revealedCount.intValue==-1 || revealedCount.intValue==10) {
            viewModelScope.launch {
                val currentDate = Date()
                val dateString = DateFormatter.formatter.format(currentDate)
                val entity = scoreDao.getScoreByDate(dateString)
                if (entity==null) {
                    scoreDao.insertScore(dateString, currentDate.time, 0, 0 )
                }
                if (revealedCount.intValue == 10) {
                    scoreDao.updateWinCount(dateString)
                } else if (revealedCount.intValue==-1) {
                    scoreDao.updateLossCount(dateString)
                }
            }
        }
    }

    fun updateScoreState() {
        viewModelScope.launch {
            scores.clear()
            val listOfScore = scoreDao.getScore().map {
                Score(it.date, it.winCount, it.lossCount)
            }
            scores.addAll(listOfScore)
        }
    }
}
