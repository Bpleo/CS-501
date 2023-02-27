package com.cs501.boggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var inputWord: TextView? = null
    var upperFragment = UpperFragment()
    var lowerFragment = LowerFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // setting up the upper fragment & lower fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLUpperFragment, upperFragment)
            replace(R.id.FLLowerFragment, lowerFragment)
            commit()
        }


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