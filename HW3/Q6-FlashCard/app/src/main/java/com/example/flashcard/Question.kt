package com.example.flashcard

import androidx.annotation.StringRes

data class Question(@StringRes val QuestionId: Int, val operand1: Int, val operand2: Int, val operator: Int, val answer: Int)
