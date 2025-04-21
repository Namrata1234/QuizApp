package com.example.quizapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score")
data class ScoreEntity(@PrimaryKey val id: Int = 0, val score: Int)