package com.example.quizapp.domain.usecases

import com.example.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuizDataUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke() = repository.fetchQuizData()
}