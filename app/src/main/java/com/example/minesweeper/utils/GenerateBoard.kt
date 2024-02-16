package com.example.minesweeper.utils

import com.example.minesweeper.data.Cell
import kotlin.random.Random

fun initializeBoard(size: Int) : List<List<Cell>> {
    val board = List(size) {
        List(size) {
            Cell()
        }
    }
    return board
}

fun generateBoard(board: List<List<Cell>>, minesCount: Int, cRow: Int, cCol: Int) {
    val size = board.size
    repeat(minesCount) {
        var row: Int
        var col: Int
        do {
            row = Random.nextInt(size) % size
            col = Random.nextInt(size) % size
        } while ((row==cRow && col == cCol) || board[row][col].isMined)
        updateCellAround(row, col, board)
    }
}

private fun updateCellAround(x: Int, y: Int, board: List<List<Cell>>) {
    board[x][y].isMined = true
    val dx = arrayOf(-1,1,0,0,-1,-1,1,1)
    val dy = arrayOf(0,0,-1,1,1,-1,1,-1)
    val dir = 8
    val n = board.size
    for(i in 0..<dir) {
        val nx = x + dx[i]
        val ny = y + dy[i]
        if (nx in 0..<n && ny >= 0 && ny < n) {
            board[nx][ny].minesAround++
        }
    }
}
