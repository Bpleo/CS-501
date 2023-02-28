package com.example.hangmangame

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class GameViewModelTest{
    @Test
    fun GenerateUnderScoreWordsExpected(){
        val gameViewModel=GameViewModel()
        //test if it will generate the correct initial underscore word
        assertEquals("------",GameManager().generateUnderScore(6))
        //test if it will generate the correct underscore word when running the game

        gameViewModel.currentWordToGuess="jungle"
        gameViewModel.playGame('J')
        assertEquals("J-----",gameViewModel.currentUnderscoreWord)

    }

    @Test
    fun startGameTest() {
        val gameViewModel=GameViewModel()
        gameViewModel.startNewGame()
        assertEquals(gameViewModel.currentHintCount, 0)
        assertEquals(gameViewModel.currentLettersUsed, "")
        assertEquals(gameViewModel.currentTries, 0)
        assertEquals(gameViewModel.currentGameState, 2)
        assertEquals(gameViewModel.currentUnderscoreWord.length, gameViewModel.currentWordToGuess.length)
    }
}