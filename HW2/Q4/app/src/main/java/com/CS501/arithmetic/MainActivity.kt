package com.CS501.arithmetic

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var result : TextView
    lateinit var first_num : EditText
    lateinit var second_num : EditText
    lateinit var add_button : RadioButton
    lateinit var sub_button : RadioButton
    lateinit var mul_button : RadioButton
    lateinit var div_button : RadioButton
    lateinit var mod_button : RadioButton
    lateinit var operation_selection : RadioGroup
    lateinit var operation : RadioButton
    lateinit var calculate_button : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        first_num = findViewById(R.id.first_num)
        second_num = findViewById(R.id.second_num)
        operation_selection = findViewById(R.id.operator_selection)
        calculate_button = findViewById(R.id.calculate_button)

        add_button = findViewById(R.id.add_button)
        sub_button = findViewById(R.id.subtract_button)
        mul_button = findViewById(R.id.multiply_button)
        div_button = findViewById(R.id.divide_button)
        mod_button = findViewById(R.id.mod_button)

        calculate_button.setOnClickListener {
            if (!assertNotEmpty()) return@setOnClickListener
            val first = first_num.text.toString().toInt()
            val second = second_num.text.toString().toInt()

            val selected = operation_selection.checkedRadioButtonId
            operation = findViewById(selected)

            when (operation) {
                add_button -> add(first, second)
                sub_button -> subtract(first, second)
                mul_button -> multiply(first, second)
                div_button -> divide(first, second)
                mod_button -> mod(first, second)
            }
        }
    }

    private fun assertNotEmpty(): Boolean {
        if (first_num.text.toString() != "" && second_num.text.toString() != "") {
            return true
        }

        Toast.makeText(
            this,
            R.string.empty_input_error_message,
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

    private fun add(first: Int, second: Int) {
        result.text = (first + second).toString()
    }

    private fun subtract(first: Int, second: Int) {
        result.text = (first - second).toString()
    }

    private fun multiply(first: Int, second: Int) {
        result.text = (first * second).toString()
    }

    private fun divide(first: Int, second: Int) {
        if (second != 0) {
            result.text = (first.toDouble() / second).toString()
        }
        else {
            result.text = getString(R.string.error)

            Toast.makeText(
                this,
                R.string.div_zero_error_message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun mod(first: Int, second: Int) {
        if (second != 0) {
            result.text = (first % second).toString()
        }
        else {
            result.text = getString(R.string.error)

            Toast.makeText(
                this,
                R.string.mod_zero_error_message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}