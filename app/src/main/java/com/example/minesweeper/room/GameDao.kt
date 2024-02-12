package com.example.minesweeper.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.minesweeper.data.GameEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    fun getGames(): List<GameEntity>

    @Insert
    fun insertGame(entity: GameEntity)

}
