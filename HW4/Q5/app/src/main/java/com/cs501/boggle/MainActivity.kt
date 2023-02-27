package com.cs501.boggle

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {
    private var introMessageLayout: RelativeLayout? = null
    private var appContentLayout: ConstraintLayout? = null
    private var tvWelcomeMessage: TextView? = null

    private var upperFragment = UpperFragment()
    private var lowerFragment = LowerFragment()

    private var score: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        introMessageLayout = findViewById(R.id.welcomeMessageLayout)
        appContentLayout = findViewById(R.id.appContentLayout)

        tvWelcomeMessage = findViewById(R.id.welcomeMessage)
        tvWelcomeMessage?.movementMethod = ScrollingMovementMethod()


        // setting up the upper fragment & lower fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLUpperFragment, upperFragment)
            replace(R.id.FLLowerFragment, lowerFragment)
            commit()
        }


    }

    // user click "Got it" in the welcome message box
    fun dismissWelcomeMessageBox(view: View?) {
        introMessageLayout!!.visibility = View.INVISIBLE
        appContentLayout!!.visibility = View.VISIBLE
    }

    // user click on a letter
    fun tapOnLetter(view: View) {
        upperFragment.tapOnLetter(view)
    }

    // user click on CLEAR
    fun onClear(view: View) {
        upperFragment.onClear(view)
    }

    // user click on Submit
    fun onSubmit(view: View) {
        score += upperFragment.onSubmit(view)
        upperFragment.resetBoard()
        lowerFragment.updateScore(score)
    }

    fun onNewGame(view: View) {
        introMessageLayout!!.visibility = View.VISIBLE
        appContentLayout!!.visibility = View.INVISIBLE
        upperFragment = UpperFragment()
        lowerFragment = LowerFragment()
        score = 0
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLUpperFragment, upperFragment)
            replace(R.id.FLLowerFragment, lowerFragment)
            commit()
        }
    }
}