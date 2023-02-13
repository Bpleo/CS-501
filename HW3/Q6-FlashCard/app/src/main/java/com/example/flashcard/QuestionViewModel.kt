package com.example.flashcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.random.Random

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuestionViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var questionList = ArrayList<Question>();
    private var canSubmit: Boolean = false
    private var canGenerate: Boolean = true
    private var correctCount: Int = 0
    private var count: Int = 0

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentOperand1: Int
        get() {
            return if (questionList.isEmpty())
                -1
            else
                questionList[currentIndex].operand1
        }
    val currentOperand2: Int
        get() {
            return if (questionList.isEmpty())
                -1
            else
                questionList[currentIndex].operand2
        }
    val currentOperator: Int // 1 for plus, 2 for subtract
        get() {
            return if (questionList.isEmpty())
                -1
            else
                questionList[currentIndex].operator
        }
    val currentAnswer: Int
        get() {
          return if (questionList.isEmpty())
              -1
            else
              questionList[currentIndex].answer
        }
    val currentQuestionList: ArrayList<Question>
        get()=questionList

    var currentCanSubmit: Boolean
        get() = canSubmit
        set(value) { canSubmit = value}
    var currentCanGenerate: Boolean
        get() = canGenerate
        set(value) { canGenerate = value}
    var currentCount: Int
        get() = count
        set(value) {count = value}
    var currentCorrectCount: Int
        get() = correctCount
        set(value) {correctCount = value}
    fun rePopulateList() {
        //make sure list is clear
        questionList.clear()
        canGenerate = false
        canSubmit = true
        //populate addition question
        for (i in 1..5) {
            val operand1: Int = Random.nextInt(1, 99)
            val operand2: Int = Random.nextInt(1, 20)
            val answer = operand1 + operand2
            questionList.add(Question(i,operand1,operand2,1,answer))
        }
        //populate subtraction question
        for (i in 6..10){
            val operand1: Int = Random.nextInt(1, 99)
            val operand2: Int = Random.nextInt(1, 20)
            val answer = operand1 - operand2
            questionList.add(Question(i,operand1,operand2,2,answer))
        }
        //shuffle the question list
        questionList.shuffle()
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionList.size
    }
}