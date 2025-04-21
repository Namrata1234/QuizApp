package com.example.quizapp.data.model

data class QuizResponse(
    val response_code: Int,
    val results: List<QuizItem>
)
