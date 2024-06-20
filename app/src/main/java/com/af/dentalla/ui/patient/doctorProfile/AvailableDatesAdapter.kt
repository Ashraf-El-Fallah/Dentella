package com.af.dentalla.ui.patient.doctorProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemDateBinding
import java.text.SimpleDateFormat
import java.util.*

class AvailableDatesAdapter(private val dates: List<String?>) :
    ListAdapter<String, AvailableDatesAdapter.DateViewHolder>(DiffCallback()) {

    inner class DateViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String?) {
            val (year, month, dayInfo) = parseDate(date)
            val (dayOfMonth, dayOfWeek) = dayInfo.split(", ")
            binding.yearNumber.text = year
            binding.monthNumber.text = month
            binding.dayName.text = dayOfWeek
            binding.dayNumber.text = dayOfMonth
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDateBinding.inflate(inflater, parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date)
    }


    private fun parseDate(input: String?): Triple<String, String, String> {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val date = input?.let { inputFormat.parse(it) }
        val calendar = Calendar.getInstance().apply {
            if (date != null) {
                time = date
            }
        }

        val year = calendar.get(Calendar.YEAR).toString()
        val month = (calendar.get(Calendar.MONTH) + 1).toString()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val dayOfWeek =
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

        return Triple(year, month, "$dayOfMonth, $dayOfWeek")
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