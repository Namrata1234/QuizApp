package com.example.quizapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.QuizData
import com.example.quizapp.data.model.QuizUseCases
import com.example.quizapp.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizDataUseCase: QuizUseCases,

    ) : ViewModel() {
    var _quizData by mutableStateOf<ApiState<List<QuizData>>>(ApiState.Loading)
    var userAnswers = mutableStateMapOf<Int, String>()
    var score by mutableStateOf(0)
    var lastScore by mutableStateOf(0)
    var isSubmitted by mutableStateOf(false)


    init {
        fetchQuizDataDetails()
    }

    private fun fetchQuizDataDetails() {
        viewModelScope.launch {
            lastScore = getQuizDataUseCase.getLastScore()
            _quizData = getQuizDataUseCase.fetchQuestions()
        }
    }

    fun selectAnswer(index: Int, ans: String) {
        userAnswers[index] = ans
    }

    fun submit() {
        val questions = (_quizData as? ApiState.Success)?.data ?: return
        score = questions.withIndex().count { userAnswers[it.index] == it.value.answer }
        isSubmitted = true
        viewModelScope.launch { getQuizDataUseCase.saveScore(score) }
    }

}
