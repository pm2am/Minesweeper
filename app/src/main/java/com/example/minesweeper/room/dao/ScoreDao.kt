package com.example.minesweeper.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.minesweeper.room.entity.ScoreEntity

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score_table")
    suspend fun getScore(): List<ScoreEntity>

    @Query("SELECT * FROM score_table WHERE date = :date")
    suspend fun getScoreByDate(date: String): ScoreEntity?

    @Query("INSERT INTO score_table VALUES(:id, :timeCreated, :winCount, :lossCount)")
    suspend fun insertScore(id: String, timeCreated: Long, winCount: Int, lossCount: Int)

    @Query("UPDATE score_table SET winCount = winCount + 1 WHERE date=:curId")
    suspend fun updateWinCount(curId: String)

    @Query("UPDATE score_table SET lossCount = lossCount + 1 WHERE date=:curId")
    suspend fun updateLossCount(curId: String)
//
//    @Query("DELETE FROM score_table WHERE date NOT IN (SELECT date in score_table ORDER BY timeCreated DESC LIMIT 5)")
//    suspend fun deleteOldRow()
}
