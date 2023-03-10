package com.cs501.boggle

import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import okhttp3.*
import java.io.IOException
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 * Use the [UpperFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpperFragment : Fragment() {
    private var inputWord: TextView? = null

    private var prevWordId: Int? = null
    private var idLocationMap = mapOf<Int, IntArray> ()
    private var selectedLetter = mutableListOf<Int>()
    private lateinit var fragmentView: View

    private var existWordDict = HashSet<String>()
    private val vowels = setOf('A', 'E', 'I', 'O', 'U')
    private var board1 = arrayOf('S', 'T', 'N', 'G',
                                'E', 'I', 'A', 'E',
                                'D', 'R', 'L', 'S',
                                'S', 'E', 'P', 'O' )
    private var board2 = arrayOf('S', 'P', 'L', 'D',
                                'E', 'A', 'I', 'E',
                                'D', 'R', 'T', 'N',
                                'S', 'E', 'A', 'S' )
    private var board3 = arrayOf('S', 'E', 'R', 'S',
                                'P', 'A', 'T', 'G',
                                'L', 'I', 'N', 'E',
                                'S', 'E', 'R', 'S' )
    private var boards = arrayOf(board1, board2, board3)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upper, container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
        generateRandomBoard(view)
        inputWord = view.findViewById<TextView>(R.id.inputWord)
    }

    private fun generateRandomBoard(view: View) {
        // Randomly generate the letters in the board
        val boardNum = Random.nextInt(3)
        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayout)
        var firstId = 0
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            if (i == 0) firstId = button.id
            button.text = boards[boardNum][i].toString()
            val newEntry = button.id to intArrayOf((button.id-firstId)/4, (button.id-firstId)%4)
            idLocationMap = idLocationMap.toMutableMap()
            idLocationMap += newEntry
        }
    }

    fun onSubmit(view: View): Int {
        val word = (inputWord?.text).toString().uppercase()
        if (containsTwoVowels(word) && hasEnoughLen(word) && isWordValid(word)) {
            Toast.makeText(
                activity,
                R.string.correct_message,
                Toast.LENGTH_SHORT
            ).show()
            existWordDict.add(word)
            return calculateScore(word)
        }

        if (existWordDict.contains(word)) return 0

        return -10
    }

    fun calculateScore(word: String): Int {
        var score = 0

        for (c in word) {
            score += if (c in vowels) 5 else 1
        }

        return score
    }

    fun containsTwoVowels(word: String): Boolean {
        var count = 0

        for (c in word) {
            if (c in vowels) {
                count++
                if (count >= 2) {
                    return true
                }
            }
        }

        Toast.makeText(
            activity,
            R.string.not_enough_vowels_incorrect_message,
            Toast.LENGTH_SHORT
        ).show()

        resetBoard()

        return false
    }

    fun hasEnoughLen(word: String): Boolean {
        if (word.length >= 4) {
            return true
        }

        Toast.makeText(
            activity,
            R.string.not_enough_length_incorrect_message,
            Toast.LENGTH_SHORT
        ).show()

        resetBoard()

        return false
    }

    fun isWordValid(word: String): Boolean {
        if (existWordDict.contains(word)) {
            Toast.makeText(
                activity,
                R.string.word_exist_incorrect_message,
                Toast.LENGTH_SHORT
            ).show()

            resetBoard()

            return false
        }

        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

        val url = "https://raw.githubusercontent.com/dwyl/english-words/master/words.txt"
        val wordList = downloadWordList(url).split("\n").toSet().map { it.uppercase() }
        if (word !in wordList) {
            Toast.makeText(
                activity,
                R.string.word_not_valid_incorrect_message,
                Toast.LENGTH_SHORT
            ).show()

            resetBoard()

            return false
        }

        return true
    }

    private fun downloadWordList(url: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        return response.body?.string() ?: ""
    }



    fun onClear(view: View) {
        resetBoard()
    }

    fun resetBoard() {
        for (id in selectedLetter) {
            fragmentView.findViewById<Button>(id).setBackgroundColor(resources.getColor(R.color.purple_500))
            fragmentView.findViewById<Button>(id).isEnabled = true
        }
        selectedLetter = mutableListOf<Int>()
        inputWord?.text = ""
        prevWordId = null
    }

    fun resetGame() {
        resetBoard()
        existWordDict.clear() // Clear HashSet
    }

    fun tapOnLetter(view: View) {
        var id = view.id
        if (isValidInput(id)) {
            inputWord?.append((view as Button).text)
            prevWordId = id
            (view as Button).setBackgroundColor(Color.RED)
            (view as Button).isEnabled = false
            selectedLetter.add(id)
        } else {
            Toast.makeText(
                activity,
                R.string.not_adjacent_click_error_message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun isValidInput(currWordId: Int): Boolean {
        if (prevWordId==null) return true
        return areAdjacent(idLocationMap[prevWordId]?.get(0) ?: -1,
            idLocationMap[prevWordId]?.get(1) ?: -1,
            idLocationMap[currWordId]?.get(0) ?: -1,
            idLocationMap[currWordId]?.get(1) ?: -1)
    }

    fun areAdjacent(row1: Int, col1: Int, row2: Int, col2: Int): Boolean {
        val rowDiff = kotlin.math.abs(row1 - row2)
        val colDiff = kotlin.math.abs(col1 - col2)
        return rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff) != 0
    }

}