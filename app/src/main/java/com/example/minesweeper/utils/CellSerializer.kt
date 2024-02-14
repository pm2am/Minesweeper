package com.example.minesweeper.utils

import com.example.minesweeper.data.Cell
import org.json.JSONArray

object CellSerializer {
    fun serialize(cells: List<List<Cell>>): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append("[")
        for (row in cells) {
            stringBuilder.append("[")
            for (cell in row) {
                stringBuilder.append("{")
                stringBuilder.append("\"isMined\": ${cell.isMined},")
                stringBuilder.append("\"isRevealed\": ${cell.isRevealed},")
                stringBuilder.append("\"minesAround\": ${cell.minesAround}")
                stringBuilder.append("},")
            }
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
            stringBuilder.append("],")
        }
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        stringBuilder.append("]")

        return stringBuilder.toString()
    }

    fun deserialize(json: String): List<List<Cell>> {
        val list = mutableListOf<List<Cell>>()
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            val innerArray = jsonArray.getJSONArray(i)
            val row = mutableListOf<Cell>()
            for (j in 0 until innerArray.length()) {
                val jsonObject = innerArray.getJSONObject(j)
                val isMined = jsonObject.getBoolean("isMined")
                val isRevealed = jsonObject.getBoolean("isRevealed")
                val minesAround = jsonObject.getInt("minesAround")
                val cell = Cell(isMined, isRevealed, minesAround)
                row.add(cell)
            }
            list.add(row)
        }

        return list
    }
}
