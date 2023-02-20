package com.example.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.criminalintent.databinding.ListItemCrimeBinding
import com.example.criminalintent.databinding.ListItemSeriousCrimeBinding

class CrimeHolder(
    private val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class SeriousCrimeHolder(
    private val binding: ListItemSeriousCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingNoPolice = ListItemCrimeBinding.inflate(inflater, parent, false)
        val bindingPolice = ListItemSeriousCrimeBinding.inflate(inflater, parent, false)
        if (viewType == TYPE_NO_POLICE)
            return CrimeHolder(bindingNoPolice)
        else
            return SeriousCrimeHolder(bindingPolice)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crime = crimes[position]
        if (crime.requiresPolice)
            (holder as SeriousCrimeHolder).bind(crime)
        else
            (holder as CrimeHolder).bind(crime)
    }
    override fun getItemCount() = crimes.size
    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]
        if (crime.requiresPolice)
            return TYPE_POLICE
        else
            return TYPE_NO_POLICE
    }

    companion object{
        private const val TYPE_POLICE = 1
        private const val TYPE_NO_POLICE = 0
    }
}
