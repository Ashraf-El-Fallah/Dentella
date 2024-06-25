package com.af.dentalla.ui.patient.doctorProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemSpecialityStringBinding

class AvailableSpecialitiesAdapter(private val availableSpecialities: List<String>) :
    RecyclerView.Adapter<AvailableSpecialitiesAdapter.AvailableSpecialitiesViewHolder>() {

    inner class AvailableSpecialitiesViewHolder(private val binding: ItemSpecialityStringBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.textViewDoctorSpeciality.text = text
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableSpecialitiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AvailableSpecialitiesViewHolder(
            ItemSpecialityStringBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return availableSpecialities.size
    }

    override fun onBindViewHolder(holder: AvailableSpecialitiesViewHolder, position: Int) {
        val speciality = availableSpecialities[position]
        holder.bind(speciality)
    }
}