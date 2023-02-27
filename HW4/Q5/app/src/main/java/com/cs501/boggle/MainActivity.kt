package com.cs501.boggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var upperFragment = UpperFragment()
        var lowerFragment = LowerFragment()

        // setting up the upper fragment & lower fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FLUpperFragment, upperFragment)
            replace(R.id.FLLowerFragment, lowerFragment)
            commit()
        }
    }
}