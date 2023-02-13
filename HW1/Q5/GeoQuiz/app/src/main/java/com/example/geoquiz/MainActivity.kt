package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.example.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var checked: Boolean = false
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result
        if (result.resultCode == Activity.RESULT_OK){
            quizViewModel.isCheater = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false ) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.trueButton.isEnabled = quizViewModel.currentTrueAble
        binding.falseButton.isEnabled = quizViewModel.currentFalseAble
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        binding.trueButton.setOnClickListener { view: View ->
//            Toast.makeText(
//                this,
//                R.string.correct_toast,
//                Toast.LENGTH_SHORT
//            ).show()
            checkAnswer(true, view)
        }

        binding.falseButton.setOnClickListener { view: View ->
//            Toast.makeText(
//                this,
//                R.string.incorrect_toast,
//                Toast.LENGTH_SHORT
//            ).show()
            checkAnswer(false, view)
        }

        binding.questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
            // Start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }


        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        binding.trueButton.isEnabled = quizViewModel.currentTrueAble
        binding.falseButton.isEnabled = quizViewModel.currentFalseAble
    }

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Snackbar.make(view, messageResId, BaseTransientBottomBar.LENGTH_SHORT
        ).show()
        quizViewModel.currentTrueAble = false
        quizViewModel.currentFalseAble = false
        binding.trueButton.isEnabled = quizViewModel.currentTrueAble
        binding.falseButton.isEnabled = quizViewModel.currentFalseAble
    }


}