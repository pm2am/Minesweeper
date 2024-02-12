package com.example.minesweeper.utils

import com.example.minesweeper.data.Cell
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CellSerializer {
    private val gson = Gson()

    fun serialize(cells: List<List<Cell>>) : String {
        return gson.toJson(cells)
    }

    fun deserialize(cellJson: String): List<List<Cell>> {
        val type = object : TypeToken<List<List<Cell>>>() {}.type
        return gson.fromJson(cellJson, type)
    }
}