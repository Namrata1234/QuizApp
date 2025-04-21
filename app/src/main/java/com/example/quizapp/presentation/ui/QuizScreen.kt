package com.example.quizapp.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.model.QuizData
import com.example.quizapp.presentation.viewmodel.QuizViewModel
import com.example.quizapp.utils.ApiState
import com.example.quizapp.utils.radiantBox
import kotlin.system.exitProcess

//QUIZ SCREEN
@Composable
fun QuizScreen(viewModel: QuizViewModel = hiltViewModel()) {
    val state = viewModel._quizData

    BackHandler {
        exitProcess(0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(radiantBox())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            //LAST SCORE DISPLAY
            Text(
                "Last Score: ${viewModel.lastScore}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )


            when (state) {
                is ApiState.Loading -> {
                    LoadingScreen()
                }

                is ApiState.Error -> {
                    Text("Error: ${(state as ApiState.Error).message}", color = Color.Red)
                }

                is ApiState.Success -> {
                    val qList = (state as ApiState.Success<List<QuizData>>).data

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 8.dp) // spacing before the submit button
                    ) {
                        itemsIndexed(qList) { idx, q ->
                            Card(Modifier.padding(8.dp)) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(
                                        "Q${idx + 1}: ${q.question}",
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    q.options.forEach { option ->
                                        Row(Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                if (!viewModel.isSubmitted) viewModel.selectAnswer(
                                                    idx,
                                                    option
                                                )
                                            }
                                            .padding(vertical = 4.dp)) {
                                            RadioButton(
                                                selected = viewModel.userAnswers[idx] == option,
                                                onClick = null
                                            )
                                            Text(
                                                HtmlCompat.fromHtml(
                                                    option,
                                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                                )
                                                    .toString(),
                                                modifier = Modifier.padding(start = 8.dp),
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                        }
                                    }

                                    if (viewModel.isSubmitted) {

                                        val correct = HtmlCompat.fromHtml(
                                            q.answer,
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                        )
                                            .toString()
                                        val userAns = HtmlCompat.fromHtml(
                                            viewModel.userAnswers[idx].toString(),
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                        )
                                            .toString()
                                        Text(
                                            "Correct: $correct",
                                            color = if (userAns == correct) Color.Green else Color.Red,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { viewModel.submit() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2be4dc)),
                            enabled = !viewModel.isSubmitted,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Submit")
                        }

                        if (viewModel.isSubmitted) {
                            Text(
                                "Your Score: ${viewModel.score}/${qList.size}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium, color = Color.White,
                                modifier = Modifier.padding(top = 8.dp, bottom = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
