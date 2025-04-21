package com.example.quizapp.data.model

import com.example.quizapp.domain.usecases.GetLastScoreUseCase
import com.example.quizapp.domain.usecases.GetQuizDataUseCase
import com.example.quizapp.domain.usecases.SaveScoreUseCase

data class QuizUseCases(
    val fetchQuestions: GetQuizDataUseCase,
    val saveScore: SaveScoreUseCase,
    val getLastScore: GetLastScoreUseCase
)