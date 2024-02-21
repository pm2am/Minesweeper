package com.example.minesweeper.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class, ScoreEntity::class], version = 1, exportSchema = false)
abstract class GameDatabase: RoomDatabase() {
    abstract fun getGameDao(): GameDao

    abstract fun getScoreDao(): ScoreDao

}
