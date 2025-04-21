package com.example.quizapp.data.remote

import com.example.quizapp.data.model.QuizResponse
import retrofit2.http.GET

interface APIService{

    @GET("api.php?amount=10&category=9&difficulty=medium&type=multiple")
    suspend fun getQuizData(): QuizResponse
}