package com.example.hangmangame

import android.widget.TextView
import androidx.lifecycle.ViewModel
import org.w3c.dom.Text
import kotlin.random.Random

class GameViewModel:ViewModel() {
    private var gameState: Int=2;
    //0: lost  1: win  2:running
    private var lettersUsed: String=""
    private var currTries: Int = 0
    private var drawable: Int=R.drawable.game01;
    private var wordToGuess: String=generateWord();
    private var underscoreWord: String=GameManager().generateUnderScore(wordToGuess.length);
    private var hintCount: Int=0;
    private var letterIsVisibility: HashMap<Int, Boolean> =HashMap()
    private var letterColor: HashMap<Int, Int> =HashMap()
    private var hintButtonEnabled:Boolean=true
    fun getLetterIsVisibility():HashMap<Int, Boolean>  {
        return letterIsVisibility
    }
    fun addLetterIsVisibility(id:Int, visible:Boolean){
        letterIsVisibility[id] = visible
    }
    fun getLetterColor():HashMap<Int,Int>{
        return letterColor
    }
    fun addLetterColor(id:Int,color:Int){
        letterColor[id]=color
    }
    fun getHintButtonEnabled():Boolean{
        return hintButtonEnabled
    }
    fun setHintButtonEnabled(state: Boolean){
        hintButtonEnabled=state
    }

    var currentGameState:Int
        get() = gameState
        set(value) {gameState=value}

    var currentLettersUsed:String
        get() = lettersUsed
        set(value) {lettersUsed=value}

    var currentTries:Int
        get() = currTries
        set(value) {currTries=value}
    var currentDrawable:Int
        get() = drawable
        set(value) {drawable=value }
    var currentWordToGuess: String
        get() = wordToGuess
        set(value) {wordToGuess=value}
    var currentUnderscoreWord:String
        get() = underscoreWord
        set(value) {underscoreWord=value}

    var currentHintCount:Int
        get() = hintCount
        set(value) {hintCount=value}

    fun startNewGame(){
        hintCount=0;
        lettersUsed="";
        currTries=0;
        drawable=R.drawable.game01
        wordToGuess=generateWord()
        underscoreWord=GameManager().generateUnderScore(wordToGuess.length)
        gameState=2
    }

    private fun generateWord(): String {
        return GameConstants.words[Random.nextInt(GameConstants.words.size)]
    }
    private fun judgeGameState(){
        if(underscoreWord.uppercase() == wordToGuess.uppercase()) {
            gameState=1 //win
        } else if(currTries==GameConstants.maxTries){
            gameState=0; //lost
        }else{
            drawable=GameManager().getDrawable(currTries)
            gameState=2//game running
        }
    }
    fun playGame(letter: Char){
        if(lettersUsed.contains(letter)){
            gameState=2
            return;
        }
        lettersUsed+=letter;
        val indexes=ArrayList<Int>();
        wordToGuess.forEachIndexed { index, char ->
            if (char.equals(letter, true)) {
                indexes.add(index)
            }
        }

        if(indexes.size ==0){
            currTries++;
        }
        var updatedUnderScore=""+underscoreWord;
        indexes.forEach { index ->
            val sb = StringBuilder(updatedUnderScore).also { it.setCharAt(index, letter) }
            updatedUnderScore = sb.toString()
        }
        underscoreWord=updatedUnderScore
        judgeGameState()

    }

}