package com.af.dentalla.ui.patient.doctorProfile

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemDateBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class AvailableDatesAdapter(private val availableDates: List<String>) : ListAdapter<String, AvailableDatesAdapter.DateViewHolder>(DiffCallback()) {
    inner class DateViewHolder(private val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(date: String) {
            val (month, dayName, dayNumber) = parseDate(date)
            binding.monthNumber.text = month
            binding.dayName.text = dayName
            binding.dayNumber.text = dayNumber.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDateBinding.inflate(inflater, parent, false)
        return DateViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
//        if (position != RecyclerView.NO_POSITION && position < itemCount) {
            val date = getItem(position)
            holder.bind(date)
//        } else {
//            Log.e("Adapter", "Invalid position: $position")
//        }
    }

//    override fun getItemCount(): Int {
//        return availableDates.size
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDate(input: String): Triple<String, String, Int> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val dateTime = LocalDateTime.parse(input, formatter)
        val month = dateTime.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val dayOfWeek = dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val dayOfMonth = dateTime.dayOfMonth
        return Triple(month, dayOfWeek, dayOfMonth)
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
