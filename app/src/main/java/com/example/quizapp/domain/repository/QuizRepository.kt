package com.example.quizapp.domain.repository

import com.example.quizapp.data.model.QuizData
import com.example.quizapp.utils.ApiState

interface QuizRepository {

    suspend fun fetchQuizData(): ApiState<List<QuizData>>
    suspend fun saveScore(score: Int)
    suspend fun getLastScore(): Int
}
