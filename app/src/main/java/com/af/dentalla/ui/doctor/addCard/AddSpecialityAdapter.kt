package com.af.dentalla.ui.doctor.addCard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemChooseSpecialityBinding

class AddSpecialityAdapter(
    private val specialities: List<SpecialityAddCard>,
    private val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<AddSpecialityAdapter.AddSpecialityViewHolder>() {
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    fun setSelectedItem(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }

    inner class AddSpecialityViewHolder(private val binding: ItemChooseSpecialityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speciality: SpecialityAddCard) {
            binding.itemSpeciality.apply {
                imgSpeciality.setImageResource(
                    if (adapterPosition == selectedItemPosition) {
                        speciality.imageDrawableWhite
                    } else {
                        speciality.imageDrawableBlack
                    }
                )
                textViewSpeciality.text = speciality.name

                if (adapterPosition == selectedItemPosition) {
                    root.setBackgroundColor(Color.parseColor("#1480EC"))
                } else {
                    root.setBackgroundColor(Color.TRANSPARENT)
                }
                root.setOnClickListener {
                    onItemClick(speciality.id)
                    setSelectedItem(adapterPosition)
                }
            }
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