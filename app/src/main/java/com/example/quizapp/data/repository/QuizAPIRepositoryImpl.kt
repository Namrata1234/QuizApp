package com.example.quizapp.data.repository

import androidx.core.text.HtmlCompat
import com.example.quizapp.data.database.ScoreDao
import com.example.quizapp.data.database.ScoreEntity
import com.example.quizapp.data.model.QuizData
import com.example.quizapp.data.remote.APIService
import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.utils.ApiState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class QuizAPIRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val dao: ScoreDao

) : QuizRepository {
    override suspend fun fetchQuizData(): ApiState<List<QuizData>> {
        return try {
            val response = apiService.getQuizData()
            val result = response.results.map {
                QuizData(
                    question = HtmlCompat.fromHtml(it.question, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        .toString(),
                    options = (it.incorrect_answers + it.correct_answer).shuffled(),
                    answer = it.correct_answer
                )
            }
            ApiState.Success(result)
        } catch (e: IOException) {
            ApiState.Error("Network Error: Unable to fetch data")
        } catch (e: HttpException) {
            ApiState.Error("Server Error: ${e.message()}")
        }
    }
    override suspend fun saveScore(score: Int) = dao.saveScore(ScoreEntity(score = score))
    override suspend fun getLastScore() = dao.getScore()?.score ?: 0
}
