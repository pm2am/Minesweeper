package com.example.minesweeper.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score_table WHERE id = :curId")
    suspend fun getScore(curId: Long): ScoreEntity

    @Query("INSERT INTO score_table VALUES(:id, :winCount, :lossCount)")
    suspend fun insertScore(id: Long, winCount: Int, lossCount: Int)

    @Query("UPDATE score_table SET winCount = winCount + 1 WHERE id=:curId")
    suspend fun updateWinCount(curId: Long)

    @Query("UPDATE score_table SET lossCount = lossCount + 1 WHERE id=:curId")
    suspend fun updateLossCount(curId: Long)
}
