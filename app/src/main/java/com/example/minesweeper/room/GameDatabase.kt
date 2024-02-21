package com.example.minesweeper.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.minesweeper.room.dao.GameDao
import com.example.minesweeper.room.dao.ScoreDao
import com.example.minesweeper.room.entity.GameEntity
import com.example.minesweeper.room.entity.ScoreEntity

@Database(entities = [GameEntity::class, ScoreEntity::class], version = 1, exportSchema = false)
abstract class GameDatabase: RoomDatabase() {
    abstract fun getGameDao(): GameDao

    abstract fun getScoreDao(): ScoreDao

}
