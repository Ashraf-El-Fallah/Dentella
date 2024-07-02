package com.af.dentalla.ui.patient.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemSpecialityBinding

class SpecialtyAdapter(
    private val specialities: List<Speciality>,
    private val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<SpecialtyAdapter.SpecialityViewHolder>() {

    inner class SpecialityViewHolder(private val binding: ItemSpecialityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speciality: Speciality) {
            binding.itemSpeciality.apply {
                imgSpeciality.setImageResource(speciality.imageDrawable)
                textViewSpeciality.text = speciality.name
                itemView.setOnClickListener {
                    onItemClick(speciality.id)
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecialityViewHolder {
        return SpecialityViewHolder(
            ItemSpecialityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return specialities.size
    }

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        holder.bind(specialities[position])
    }
}