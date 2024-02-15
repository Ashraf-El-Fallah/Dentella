package com.af.dentalla.ui.patient.homeScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemSpecialityBinding

class SpecialtyAdapter(private val specialities: List<Speciality>) :
    RecyclerView.Adapter<SpecialtyAdapter.SpecialityViewHolderViewHolder>() {

    inner class SpecialityViewHolderViewHolder(private val binding: ItemSpecialityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speciality: Speciality) {
            binding.imgSpeciality.setImageResource(speciality.imageDrawable)
            binding.textViewSpeciality.text = speciality.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecialityViewHolderViewHolder {
        return SpecialityViewHolderViewHolder(
            ItemSpecialityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return specialities.size
    }

    override fun onBindViewHolder(holder: SpecialityViewHolderViewHolder, position: Int) {
        holder.bind(specialities[position])
    }
}