package com.cs501.boggle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 * Use the [LowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LowerFragment : Fragment() {

    private var tvScore: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lower, container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvScore = view.findViewById(R.id.txtScore)
    }

    fun updateScore(newScore: Int) {
        println("New score"+ newScore)
        tvScore?.text = "Score: $newScore"
    }

}