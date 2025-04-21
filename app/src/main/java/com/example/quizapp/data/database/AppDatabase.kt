package com.example.quizapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScoreEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}