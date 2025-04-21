package com.example.quizapp.di

import android.content.Context
import androidx.room.Room
import com.example.quizapp.data.database.AppDatabase
import com.example.quizapp.data.database.ScoreDao
import com.example.quizapp.data.model.QuizUseCases
import com.example.quizapp.data.remote.APIService
import com.example.quizapp.data.repository.QuizAPIRepositoryImpl
import com.example.quizapp.domain.repository.QuizRepository
import com.example.quizapp.domain.usecases.GetLastScoreUseCase
import com.example.quizapp.domain.usecases.GetQuizDataUseCase
import com.example.quizapp.domain.usecases.SaveScoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    //LOGGING INTERCEPTOR
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //OKHTTP CLIENT
    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor).build()
    }

    // RETROFIT CLIENT
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    // Provides Room database instance for storing score locally
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "quiz_db").build()


    // Provides DAO from Room database for score operations
    @Provides
    fun provideScoreDao(db: AppDatabase): ScoreDao = db.scoreDao()

    // API SERVICE
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    // Provides UseCase container to encapsulate business logic for clean architecture
    @Provides
    fun provideUseCases(repo: QuizRepository): QuizUseCases = QuizUseCases(
        fetchQuestions = GetQuizDataUseCase(repo),
        saveScore = SaveScoreUseCase(repo),
        getLastScore = GetLastScoreUseCase(repo)
    )

    // Provides Repository implementation to abstract data operations
    @Provides
    fun provideRepository(api: APIService, dao: ScoreDao): QuizRepository =
        QuizAPIRepositoryImpl(api, dao)


}