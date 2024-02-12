package com.example.minesweeper.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.minesweeper.data.GameEntity

@Database(entities = [GameEntity::class], version = 1)
abstract class GameDatabase: RoomDatabase() {
    abstract fun getDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase?= null

        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}