package com.example.minesweeper.utis

import com.example.minesweeper.utils.initializeBoard
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

}
