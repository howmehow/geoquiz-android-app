package com.howmehow.android.geoquiz

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
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
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        previousButton.setOnClickListener {
            if (currentIndex > 0){currentIndex = (currentIndex - 1) % questionBank.size}
            updateQuestion()
        }

        questionTextView.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
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
        Log.d(TAG, "onDestory is called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause is called")
    }
    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        enableButtons()
    }
    var currentScore = 0
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        if (currentScore == 6 || currentIndex == 6){currentScore = 0}
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