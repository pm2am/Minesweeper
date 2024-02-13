package com.example.minesweeper.utils

import com.example.minesweeper.data.Cell
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object CellSerializer {
    val gson = GsonBuilder()
    .registerTypeAdapter(Cell::class.java, CellAdapter)
    .create()


    fun serialize(cells: List<List<Cell>>) : String {
        return gson.toJson(cells)
    }

    fun deserialize(cellJson: String): List<List<Cell>> {
        val type = object : TypeToken<List<List<Cell>>>() {}.type
        return gson.fromJson(cellJson, type)
    }
}