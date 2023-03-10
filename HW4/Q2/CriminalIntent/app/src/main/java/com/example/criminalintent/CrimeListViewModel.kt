package com.example.criminalintent

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.random.Random

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()
    init {
        for (i in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title ="Crime #$i",
                date = Date(),
                isSolved = i % 2 == 0,
                requiresPolice = (i + Random.nextInt(0,100)) % 2 == 0
            )
            crimes += crime
        }
    }
}