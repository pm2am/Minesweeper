package com.example.minesweeper.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cells: String, // Serialized representation of the list of cells
    val revealedCount: Int,
    val timer: Int
)