package com.example.minesweeper.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.minesweeper.room.entity.GameEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getGames(): List<GameEntity>

    @Insert
    suspend fun insertGame(entity: GameEntity)

    @Query("DELETE FROM game_table")
    suspend fun deleteRecord()
}
