package com.howmehow.android.geoquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

//        val provider: ViewModelProvider = ViewModelProviders.of(this)
//        val quizViewModel = provider.get(QuizViewModel::class.java)
//        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            disableButtons()
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            disableButtons()
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }
        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            startActivity(intent)
        }
        questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart is called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume is called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop is called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy is called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause is called")
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        enableButtons()
    }
    var currentScore = 0
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (currentScore == 6){currentScore = 0}
        if (currentScore < 6){
        if (userAnswer == correctAnswer){currentScore += 1}}

        var currentScoreInPercent = currentScore * 100 / 6
        val messageResId = if (userAnswer == correctAnswer){
            "Your answer is correct! $currentScoreInPercent% of correct answers."
        } else {
            "Your answer is incorrect! $currentScoreInPercent% of correct answers."
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun disableButtons(){
        trueButton?.isEnabled = false
        falseButton?.isEnabled = false
    }
    private fun enableButtons(){
        trueButton?.isEnabled = true
        falseButton?.isEnabled = true
    }
}