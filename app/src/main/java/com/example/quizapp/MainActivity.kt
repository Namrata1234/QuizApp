package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.quizapp.presentation.ui.QuizScreen
import com.example.quizapp.presentation.ui.StartScreen
import com.example.quizapp.presentation.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                App()
            }
        }
    }
}

//NAVIGATION GRAPH
@Composable
fun App(viewModel: QuizViewModel = hiltViewModel()){
    val navController= rememberNavController()
    NavHost(navController, startDestination = "start_screen") {
        composable("start_screen") { StartScreen(navController,viewModel) }
        composable("quiz_screen") { QuizScreen(viewModel) }
    }
}

