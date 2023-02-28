package com.example.hangmangame

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun preTest() {
        onView(withId(R.id.title))
            .check(matches(withText("Hangman")))
        onView(withId(R.id.playButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.playButton))
            .check(matches(withText("START GAME")))
    }

    @Test
    fun testStart() {
        onView(withId(R.id.playButton))
            .perform(click())
        onView(withId(R.id.gameLostTextView))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.gameWonTextView))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.newGameButton))
            .perform(click())
        onView(withText("CHOOSE A LETTER"))
            .check(matches(isDisplayed()))
        onView(withText("------"))
            .check(matches(isDisplayed()))
        val resources = InstrumentationRegistry.getInstrumentation().context.resources
        for (letter in 'A'..'Z') {
            onView(withText("${letter}"))
                .check(matches(isDisplayed()))
        }
        onView(withId(R.id.newGameButton))
            .check(matches(isDisplayed()))
        onView(withId(R.id.hintButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkUsedLetter() {
        onView(withId(R.id.playButton))
            .perform(click())
        onView(withId(R.id.newGameButton))
            .perform(click())
        onView(withId(R.id.a))
            .perform(click())
        onView(withId(R.id.lettersUsedTextView))
            .check(matches(withText("A")))
    }

    @Test
    fun checkRestart() {
        onView(withId(R.id.playButton))
            .perform(click())
        onView(withId(R.id.newGameButton))
            .perform(click())
        onView(withId(R.id.a))
            .perform(click())
        onView(withId(R.id.lettersUsedTextView))
            .check(matches(withText("A")))
        onView(withId(R.id.newGameButton))
            .perform(click())
        onView(withId(R.id.lettersUsedTextView))
            .check(matches(withText("")))
    }

    @Test
    fun checkLose() {
        onView(withId(R.id.playButton))
            .perform(click())
        onView(withId(R.id.newGameButton))
            .perform(click())
        onView(withId(R.id.a))
            .perform(click())
        onView(withId(R.id.b))
            .perform(click())
        onView(withId(R.id.c))
            .perform(click())
        onView(withId(R.id.d))
            .perform(click())
        onView(withId(R.id.e))
            .perform(click())
        onView(withId(R.id.f))
            .perform(click())
        onView(withId(R.id.g))
            .perform(click())
        try {
            onView(withId(R.id.h))
                .perform(click())
            onView(withId(R.id.i))
                .perform(click())
            onView(withId(R.id.j))
                .perform(click())
            onView(withId(R.id.k))
                .perform(click())
            onView(withId(R.id.l))
                .perform(click())
        }catch (e: PerformException) {

        }
        onView(withId(R.id.gameLostTextView))
            .check(matches(isDisplayed()))

    }
}