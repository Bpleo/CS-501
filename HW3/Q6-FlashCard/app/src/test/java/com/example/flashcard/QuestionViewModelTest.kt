package com.example.flashcard

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuestionViewModelTest{
    @Test
    fun GenerateQuestionsNumExpected(){
        val savedStateHandle=SavedStateHandle()
        val questionViewModel=QuestionViewModel(savedStateHandle)
        questionViewModel.rePopulateList()
        //check if system generates 10 questions
        assertEquals(10,questionViewModel.currentQuestionList.size)

        val questionList=questionViewModel.currentQuestionList
        var addQuestions=ArrayList<Question>()
        var subQuestions=ArrayList<Question>()
        for (q in questionList){
            if(q.operator==1){
                addQuestions.add(q);
            }else if (q.operator==2){
                subQuestions.add(q)
            }
        }
        //check number of addition problems
        assertEquals(5,addQuestions.size)

        //check number of subtraction problems
        assertEquals(5,subQuestions.size)

    }
}