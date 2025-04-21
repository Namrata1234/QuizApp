package com.example.quizapp.data.model

data class QuizItem(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)
