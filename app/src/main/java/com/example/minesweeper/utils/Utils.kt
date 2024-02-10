package com.example.minesweeper.utils

import com.example.minesweeper.data.Cell
import java.util.LinkedList
import java.util.Queue

fun CellBFS(row: Int, col: Int, data: Array<Array<Cell>>) {
    val dx = arrayOf(-1,1,0,0,-1,-1,1,1)
    val dy = arrayOf(0,0,-1,1,1,-1,1,-1)
    val q: Queue<Array<Int>> = LinkedList()
    q.add(arrayOf(row, col))
    while (!q.isEmpty()) {
        val (r,c) = q.poll()
        data[r][c].isRevealed = true
        for (i in 0..7) {
            val nx = r + dx[i]
            val ny = c + dy[i]
            if(nx<0 || nx>=data.size || ny<0 || ny>=data.size || data[nx][ny].isRevealed) continue
            if (data[nx][ny].minesAround==0) {
                q.add(arrayOf(nx,ny))
            } else {
                data[nx][ny].isRevealed = true
            }
        }
    }
}