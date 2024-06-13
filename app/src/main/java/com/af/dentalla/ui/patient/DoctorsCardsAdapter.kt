package com.af.dentalla.ui.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemDoctorBinding
import com.af.dentalla.domain.entity.CardsEntity
import com.bumptech.glide.Glide

class DoctorsCardsAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onInfoClick: (Int) -> Unit
) : ListAdapter<CardsEntity, DoctorsCardsAdapter.AllDoctorsCardsViewHolder>(CardDiffCallback()) {

    inner class AllDoctorsCardsViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: CardsEntity) {
            binding.apply {
                Glide.with(root.context)
                    .load(card.doctorPhoto)
                    .into(imgDoctor)
                textViewDoctorName.text = card.doctorName
                textViewCity.text = card.currentUniversity
                textViewPhoneNumber.text = card.phoneNumber
                buttonSeeInfo.setOnClickListener {
                    onInfoClick(card.cardId)
                }
            }
            binding.root.setOnClickListener {
                onItemClick(card.cardId)
            }

        }
    }

    private class CardDiffCallback : DiffUtil.ItemCallback<CardsEntity>() {
        override fun areItemsTheSame(oldItem: CardsEntity, newItem: CardsEntity): Boolean {
            return oldItem.cardId == newItem.cardId
        }

        override fun areContentsTheSame(oldItem: CardsEntity, newItem: CardsEntity): Boolean {
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
