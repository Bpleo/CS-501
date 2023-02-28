package com.example.criminalintent

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Test
class CrimeListViewModelTest {
    @Test
    fun checkCrimeNumber() {
        val crimeListViewModel = CrimeListViewModel()
        assertEquals(crimeListViewModel.crimes.size,100)
    }

    @Test
    fun checkSeriousCrime() {
        val crimeListViewModel = CrimeListViewModel()
        for (crime in crimeListViewModel.crimes) {
            if (crime.requiresPolice)
                assert(true)
        }
    }
}