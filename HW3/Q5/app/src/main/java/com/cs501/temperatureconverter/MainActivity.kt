package com.cs501.temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var celsiusSeekBar: SeekBar
    private lateinit var fahrenheitSeekBar: SeekBar
    private lateinit var celsiusTextView: TextView
    private lateinit var fahrenheitTextView: TextView
    private lateinit var constraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val celsiusMin = 0
        val celsiusMax = 100
        val fahrenheitMin = 0
        val fahrenheitBound = 32
        val fahrenheitMax = 212

        celsiusSeekBar = findViewById(R.id.celsius_seekbar)
        fahrenheitSeekBar = findViewById(R.id.fahrenheit_seekbar)
        celsiusTextView = findViewById(R.id.celsius_text)
        fahrenheitTextView = findViewById(R.id.fahrenheit_text)
        constraintLayout = findViewById(R.id.constraintLayout)

        celsiusSeekBar.max = celsiusMax - celsiusMin
        fahrenheitSeekBar.max = fahrenheitMax - fahrenheitMin

        celsiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val celsius = progress + celsiusMin
                    val fahrenheit = (celsius * 9 / 5) + 32
                    fahrenheitSeekBar.progress = fahrenheit - fahrenheitMin
                    updateTextViews(celsius, fahrenheit)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        fahrenheitSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    var fahrenheit = progress + fahrenheitMin
                    if (fahrenheit < fahrenheitBound) {
                        fahrenheit = fahrenheitBound
                        seekBar?.progress = fahrenheitBound - fahrenheitMin
                    }
                    val celsius = (fahrenheit - 32) * 5 / 9
                    celsiusSeekBar.progress = celsius - celsiusMin
                    updateTextViews(celsius, fahrenheit)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateTextViews(celsius: Int, fahrenheit: Int) {
        celsiusTextView.text = "$celsius°C"
        fahrenheitTextView.text = "$fahrenheit°F"
        if (celsius <= 20) {
            Snackbar.make(constraintLayout, "I wish it were warmer.", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(constraintLayout, "I wish it were colder.", Snackbar.LENGTH_SHORT).show()
        }
    }
}