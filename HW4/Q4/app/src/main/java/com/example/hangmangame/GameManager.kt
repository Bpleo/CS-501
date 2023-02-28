package com.example.hangmangame

import kotlin.random.Random


class GameManager {

    fun getDrawable(currTries:Int):Int{
        return when(currTries){
            0->R.drawable.game01
            1->R.drawable.game02
            2->R.drawable.game03
            3->R.drawable.game04
            4->R.drawable.game05
            5->R.drawable.game06
            6->R.drawable.game07
            7->R.drawable.game08
            else -> {R.drawable.game08}
        }
    }

    fun generateUnderScore(wordSize: Int):String{
        val sb=StringBuilder()
        for (index in 0..wordSize-1){
            sb.append("-");
        }
        return sb.toString();
    }

}