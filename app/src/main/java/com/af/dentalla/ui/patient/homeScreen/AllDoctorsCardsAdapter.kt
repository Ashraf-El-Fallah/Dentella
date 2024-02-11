package com.af.dentalla.ui.patient.homeScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemDoctorBinding
import com.af.dentalla.domain.entity.AllCardsEntity
import com.af.dentalla.utilities.loadImage

class AllDoctorsCardsAdapter(
    private val onItemClick: (AllCardsEntity) -> Unit
) : ListAdapter<AllCardsEntity, AllDoctorsCardsAdapter.AllDoctorsCardsViewHolder>(CardDiffCallback()) {

    inner class AllDoctorsCardsViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: AllCardsEntity) {
            binding.apply {
                imgDoctor.loadImage(card.doctorPhoto)
                textViewDoctorName.text = card.doctorName
                textViewCity.text = card.currentUniversity
                textViewPhoneNumber.text = card.phoneNumber
            }

            binding.root.setOnClickListener {
                onItemClick(card)
            }
        }
    }

    private class CardDiffCallback : DiffUtil.ItemCallback<AllCardsEntity>() {
        override fun areItemsTheSame(oldItem: AllCardsEntity, newItem: AllCardsEntity): Boolean {
            return oldItem.cardId == newItem.cardId
        }

        override fun areContentsTheSame(oldItem: AllCardsEntity, newItem: AllCardsEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllDoctorsCardsViewHolder {
        return AllDoctorsCardsViewHolder(
            ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllDoctorsCardsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}
