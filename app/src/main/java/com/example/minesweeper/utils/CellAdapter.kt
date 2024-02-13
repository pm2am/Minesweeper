package com.example.minesweeper.utils
import com.example.minesweeper.data.Cell
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

object CellAdapter : TypeAdapter<Cell>() {
    override fun write(out: JsonWriter, cell: Cell) {
        out.beginObject()
        out.name("isMined").value(cell.isMined)
        out.name("isRevealed").value(cell.isRevealed)
        out.name("minesAround").value(cell.minesAround)
        out.endObject()
    }

    override fun read(`in`: JsonReader): Cell {
        var isMined = false
        var isRevealed = false
        var minesAround = 0

        `in`.beginObject()
        while (`in`.hasNext()) {
            when (`in`.nextName()) {
                "isMined" -> isMined = `in`.nextBoolean()
                "isRevealed" -> isRevealed = `in`.nextBoolean()
                "minesAround" -> minesAround = `in`.nextInt()
                else -> `in`.skipValue()
            }
        }
        `in`.endObject()

        return Cell(isMined, isRevealed, minesAround)
    }
}
