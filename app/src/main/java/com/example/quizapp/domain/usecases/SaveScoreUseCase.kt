package com.example.quizapp.domain.usecases

import com.example.quizapp.domain.repository.QuizRepository

class SaveScoreUseCase(private val repo: QuizRepository) {
    suspend operator fun invoke(score: Int) = repo.saveScore(score)
}