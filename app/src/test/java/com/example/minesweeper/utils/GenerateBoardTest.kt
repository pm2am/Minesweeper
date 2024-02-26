package com.example.minesweeper.utils

import org.junit.Assert
import org.junit.Test

class GenerateBoardTest {
    @Test
    fun test_initializeBoard_P01() {
        val board = initializeBoard(9)
        Assert.assertNotNull(board)
    }

    @Test
    fun test_initializeBoard_P02() {
        val size = 8
        val board = initializeBoard(size)
        Assert.assertEquals(size, board.size)
    }

    @Test
    fun test_generateBoard() {
        val size = 8
        val mineCount = 10
        val board = initializeBoard(size)
        generateBoard(board = board, minesCount = mineCount, 0,0)
        var resultCount = 0
        board.forEach { cell ->
            cell.forEach {
                if (it.isMined)
                    resultCount++
            }
        }
        Assert.assertEquals(mineCount, resultCount)
    }
}
