package com.af.dentalla.ui.doctor.addCard

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentAddCardBinding
import com.af.dentalla.ui.patient.homeScreen.Speciality
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    private var selectedDate: Date? = null
    private var selectedTime: Date? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDateFromTheCalender()
        binding.selectTime.setOnClickListener {
            showTimePickerDialog()
        }
        binding.confirmButton.setOnClickListener {
            createFormatForDateAndTime()
        }
        setUpAddSpecialityRecyclerView()
    }

    private fun setDateFromTheCalender() {
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            selectedDate = calendar.time
        }
    }

    private fun createFormatForDateAndTime() {
        // Check if both date and time are selected
        if (selectedDate != null && selectedTime != null) {
            // Format the selected date
            val formattedDate =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
            // Format the selected time
            val formattedTime =
                SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(selectedTime)
            // Combine date and time
            val formattedDateTime = "$formattedDate" + "T" + "$formattedTime" + "Z"
            Toast.makeText(requireContext(), formattedDateTime, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Please select both date and time",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                selectedTime = calendar.time
            },
            hour,
            minute,
            false
        ).show()

    }

    private fun setUpAddSpecialityRecyclerView() {
        val specialtiesList = listOf(
            Speciality(R.drawable.add_cleaning, 0, "Cleaning"),
            Speciality(R.drawable.add_dental_filling, 1, "Filling"),
            Speciality(R.drawable.add_dental_crown, 2, "Crowns"),
//            Speciality(R.drawable.dental_implant, 3, "Implants"),
//            Speciality(R.drawable.add_tooth_extraction, 4, "Extraction"),
//            Speciality(R.drawable.denture, 5, "Dentures"),
        )
        val adapter = AddSpecialityAdapter(specialtiesList)
        binding.specialityRecyclerView.adapter = adapter
        binding.specialityRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }
}



