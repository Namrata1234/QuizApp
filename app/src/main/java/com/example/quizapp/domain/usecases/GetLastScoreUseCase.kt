package com.example.quizapp.domain.usecases

import com.example.quizapp.domain.repository.QuizRepository


class GetLastScoreUseCase(private val repo: QuizRepository) {
    suspend operator fun invoke(): Int = repo.getLastScore()
}