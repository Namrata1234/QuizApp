package com.example.quizapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score WHERE id = 0")
    suspend fun getScore(): ScoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScore(score: ScoreEntity)
}