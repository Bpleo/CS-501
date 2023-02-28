package com.example.hangmangame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.hangmangame.databinding.ActivityGameBinding
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

const val TAG="Test Point"
class GameActivity: AppCompatActivity() {
    private lateinit var binding:ActivityGameBinding
    private val gameViewModel: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.newGameButton.setOnClickListener {
            startNewGame();
        }
        //gameViewModel.startNewGame()
//        gameViewModel.currentWordToGuess= intent.getStringExtra("wordToGuess").toString();
//        gameViewModel.currentUnderscoreWord= intent.getStringExtra("underScore").toString()
        updateUI(gameViewModel.currentGameState)
        for(views in binding.lettersLayout.children){
            if(views is TextView){
                views.setOnClickListener{
                    gameViewModel.playGame(views.text[0])
                    views.visibility=View.GONE
                    gameViewModel.addLetterIsVisibility(views.id,false);
                    updateUI(gameViewModel.currentGameState)
                }
            }
        }
        binding.hintButton.setOnClickListener {
            showHint();
            updateUI(gameViewModel.currentGameState)
        }

    }

    private fun showHint() {
        when(gameViewModel.currentHintCount){
            0->{
                Toast.makeText(
                    this,
                    gameViewModel.currentWordToGuess,
                    Toast.LENGTH_SHORT
                ).show()
                gameViewModel.currentHintCount+=1

            }
            1->{
                if((gameViewModel.currentTries+1)==GameConstants.maxTries){
                    Toast.makeText(
                        this,
                        "Hint not available",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.hintButton.isEnabled=false
                    gameViewModel.setHintButtonEnabled(false)
                    return;
                }

                hint2DisableLetters();
            }
            2->{

                if((gameViewModel.currentTries+1)==GameConstants.maxTries){
                    Toast.makeText(
                        this,
                        "Hint not available",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.hintButton.isEnabled=false
                    gameViewModel.setHintButtonEnabled(false)
                    return;
                }

                hint3ShowVowels();

            }
            else->{
                Toast.makeText(
                    this,
                    "You have used all the hints",
                    Toast.LENGTH_SHORT
                ).show()
                binding.hintButton.isEnabled=false
                gameViewModel.setHintButtonEnabled(false)
            }
        }

    }

    private fun hintsUpdateViewModels() {
        gameViewModel.currentTries+=1
        gameViewModel.currentHintCount+=1
        gameViewModel.currentDrawable=GameManager().getDrawable(gameViewModel.currentTries)
        binding.imageView.setImageDrawable(ContextCompat.getDrawable(this,gameViewModel.currentDrawable))
    }

    private fun hint2DisableLetters(){
        hintsUpdateViewModels()
        val hintLetters=ArrayList<TextView>();
        for(views in binding.lettersLayout.children){
            if(views is TextView && views.visibility==0 &&!gameViewModel.currentWordToGuess.contains(views.text[0], ignoreCase = true)){
                hintLetters.add(views);
            }
        }
//        Log.d("currentWordToGuess",gameViewModel.currentWordToGuess)
//        for (i in 0 until hintLetters.size){
//            Log.d("hintLetters", hintLetters[i].text[0].toString())
//        }
        for(i in 0 until hintLetters.size step 2){
//            Log.d("not visible",hintLetters[i].text[0].toString())
            hintLetters[i].visibility=View.GONE
            gameViewModel.addLetterIsVisibility(hintLetters[i].id,false);
        }

    }
    private fun hint3ShowVowels() {

        hintsUpdateViewModels()
        val hintLetters=ArrayList<TextView>();

        val vowels=charArrayOf('A', 'E', 'I', 'O', 'U')

        for(views in binding.lettersLayout.children){
            if(views is TextView && views.visibility==0
                && gameViewModel.currentWordToGuess.contains(views.text[0], ignoreCase = true) && vowels.contains(views.text[0])){
                hintLetters.add(views)
                views.setBackgroundResource(R.color.red);
                gameViewModel.addLetterColor(views.id,R.color.red)
            }
        }
        updateUI(gameViewModel.currentGameState)
        Log.d("test"," ${hintLetters.size}")
//        for (i in 0 until hintLetters.size){
//            Log.d(TAG,"$i")
//            Log.d("hintLetters", hintLetters[i].text[0].toString())
//        }

        for(letter in binding.lettersLayout.children){
            if(letter is TextView){
                letter.setOnClickListener{
                    letter.visibility=View.GONE;
                    gameViewModel.playGame(letter.text[0])
                    updateUI(gameViewModel.currentGameState)

                    for(i in hintLetters){
                        i.setBackgroundResource(R.color.teal_700);
                        gameViewModel.addLetterColor(i.id,R.color.teal_700)
                    }
                    updateUI(gameViewModel.currentGameState)
                }
            }

        }
    }

    //activities when starting a new turn
    private fun startNewGame() {
        binding.gameLostTextView.visibility= View.GONE
        binding.gameWonTextView.visibility=View.GONE
        binding.hintButton.isEnabled=true;
        gameViewModel.setHintButtonEnabled(true)
        gameViewModel.startNewGame()
        binding.lettersLayout.visibility=View.VISIBLE
        for(views in binding.lettersLayout.children){
            gameViewModel.addLetterIsVisibility(views.id,true);
            views.visibility=View.VISIBLE
            views.setBackgroundResource(R.color.teal_700)
            gameViewModel.addLetterColor(views.id,R.color.teal_700)
        }
        updateUI(gameViewModel.currentGameState)
    }


    //update the user interface with game state
    private fun updateUI(state: Int) {
        when(state){
            0-> showGameLostUI(gameViewModel.currentWordToGuess);
            1-> showGameWinUI(gameViewModel.currentWordToGuess);
            2-> showGameRunningUI(gameViewModel.currentUnderscoreWord,gameViewModel.currentLettersUsed,gameViewModel.currentDrawable,
                gameViewModel.getLetterIsVisibility(),gameViewModel.getLetterColor());

        }
    }

    private fun showGameWinUI(wordToGuess: String) {
        binding.hintButton.isEnabled=false
        binding.wordTextView.text=wordToGuess.uppercase();
        binding.gameWonTextView.visibility=View.VISIBLE
        binding.lettersLayout.visibility=View.GONE
    }

    private fun showGameLostUI(wordToGuess: String) {
        binding.hintButton.isEnabled=false
        binding.wordTextView.text=wordToGuess.uppercase()
        binding.gameLostTextView.visibility=View.VISIBLE
        binding.lettersLayout.visibility=View.GONE
        binding.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.game08))

    }

    private fun showGameRunningUI(underScoreWords:String, lettersUsed: String, drawable:Int,lettersVisible:HashMap<Int,Boolean>,
                                  lettersColor:HashMap<Int,Int>){
        binding.wordTextView.text=underScoreWords
        binding.lettersUsedTextView.text=lettersUsed
        binding.imageView.setImageDrawable(ContextCompat.getDrawable(this,drawable))
        for(views in binding.lettersLayout.children){
            if(lettersVisible[views.id] == true){
                views.visibility=View.VISIBLE
            }else{
                views.visibility=View.GONE
            }
            var colorID=lettersColor.get(views.id)
            if (colorID != null) {
                views.setBackgroundResource(colorID)
            }else{
                views.setBackgroundColor(2131034753)
            }

        }
        binding.hintButton.isEnabled = gameViewModel.getHintButtonEnabled()
    }


    companion object{
        fun newIntent(packageContext: Context, wordToGuess: String,underScoreWords: String): Intent {
            return Intent(packageContext,GameActivity::class.java).apply {
                putExtra("wordToGuess",wordToGuess)
                putExtra("underScore",underScoreWords)
            }
        }
    }



}