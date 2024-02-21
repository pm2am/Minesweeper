package com.example.minesweeper.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table")
data class ScoreEntity(
    @PrimaryKey
    val id: Long = 0,
    var winCount: Int,
    var lossCount: Int
)
