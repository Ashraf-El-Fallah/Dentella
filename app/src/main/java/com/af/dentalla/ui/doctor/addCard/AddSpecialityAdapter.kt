package com.af.dentalla.ui.doctor.addCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemChooseSpecialityBinding
import com.af.dentalla.ui.patient.homeScreen.Speciality

class AddSpecialityAdapter(
    private val specialities: List<Speciality>
) :
    RecyclerView.Adapter<AddSpecialityAdapter.AddSpecialityViewHolder>() {

    inner class AddSpecialityViewHolder(private val binding: ItemChooseSpecialityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speciality: Speciality) {
            binding.itemSpeciality.imgSpeciality.setImageResource(speciality.imageDrawable)
            binding.itemSpeciality.textViewSpeciality.text = speciality.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSpecialityViewHolder {
        return AddSpecialityViewHolder(
            ItemChooseSpecialityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddSpecialityViewHolder, position: Int) {
        holder.bind(specialities[position])
    }

    override fun getItemCount(): Int {
        return specialities.size
    }


}