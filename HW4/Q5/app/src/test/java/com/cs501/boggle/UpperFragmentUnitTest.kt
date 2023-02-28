package com.cs501.boggle

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Local unit test for UpperFragment.
 */
class UpperFragmentUnitTest {
    private lateinit var upperFragment: UpperFragment

    @Before
    fun setUp() {
        upperFragment = UpperFragment()
    }

    @Test
    fun calculate_Score_isCorrect() {
        var score = upperFragment.calculateScore("SEAT")
        assertEquals(score, 12)
    }

    @Test
    fun check_Vowel_isCorrect() {
        var containTwoVowels = upperFragment.containsTwoVowels("AEIOU")
        assertEquals(containTwoVowels, true)
    }

    @Test
    fun check_Enough_Length_isCorrect() {
        var enoughLength = upperFragment.hasEnoughLen("AAAA")
        assertEquals(enoughLength, true)
    }
}