package com.cs501.boggle

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {
    var introMessageLayout: RelativeLayout? = null
    var appContentLayout: ConstraintLayout? = null
    var tvWelcomeMessage: TextView? = null

    private var inputWord: TextView? = null
    var upperFragment = UpperFragment()
    var lowerFragment = LowerFragment()
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
        upperFragment.onSubmit(view)
    }
}