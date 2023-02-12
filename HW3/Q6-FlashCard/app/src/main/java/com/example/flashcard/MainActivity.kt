package com.example.flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.flashcard.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val questionViewModel: QuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitBtn.isEnabled = questionViewModel.currentCanSubmit
        binding.generateBtn.isEnabled = questionViewModel.currentCanGenerate
        if (questionViewModel.currentOperand1 == -1)
            binding.num1.text = "*"
        else
            binding.num1.text = questionViewModel.currentOperand1.toString()
        if (questionViewModel.currentOperand2 == -1)
            binding.num2.text = "*"
        else
            binding.num2.text = questionViewModel.currentOperand2.toString()
        if (questionViewModel.currentOperator == -1)
            binding.operator.text = "+/-"
        else
            if (questionViewModel.currentOperator == 1)
                binding.operator.text = "+"
            else
                binding.operator.text = "-"
        binding.answerET.isCursorVisible = false
        binding.generateBtn.setOnClickListener {
            questionViewModel.rePopulateList()
            updateQuestion()
            questionViewModel.currentCorrectCount = 0
            questionViewModel.currentCount = 0
            binding.submitBtn.isEnabled = questionViewModel.currentCanSubmit
            binding.generateBtn.isEnabled = questionViewModel.currentCanGenerate
        }
        binding.submitBtn.setOnClickListener { view: View ->
            checkAnswer(view)
        }
    }

    private fun updateQuestion(){
        binding.num1.text = questionViewModel.currentOperand1.toString()
        binding.num2.text = questionViewModel.currentOperand2.toString()
        if (questionViewModel.currentOperator == 1)
            binding.operator.text = "+"
        else
            binding.operator.text = "-"
        questionViewModel.currentCount++
        if (questionViewModel.currentCount == 10) {
            Toast.makeText(
                this,
                "${questionViewModel.currentCorrectCount}/${questionViewModel.currentCount}",
                Toast.LENGTH_SHORT
            ).show()
            questionViewModel.currentCanSubmit = false
            questionViewModel.currentCanGenerate = true
            reset()
        }
    }

    private fun checkAnswer(view: View){
        val enteredAns: String = binding.answerET.getText().toString()
        binding.answerET.setText("")
        if (enteredAns.compareTo(questionViewModel.currentAnswer.toString()) == 0) {
            questionViewModel.currentCorrectCount++
            Snackbar.make(view,"Correct",Toast.LENGTH_SHORT).show()
        } else {
            Snackbar.make(view,"Incorrect",Toast.LENGTH_SHORT).show()
        }
        questionViewModel.moveToNext()
        updateQuestion()
    }

    private fun reset(){
        binding.submitBtn.isEnabled = questionViewModel.currentCanSubmit
        binding.generateBtn.isEnabled = questionViewModel.currentCanGenerate
        binding.num1.text = "*"
        binding.num2.text = "*"
        binding.operator.text = "+/-"
    }
}