package com.af.dentalla.ui.doctor.addCard

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentAddCardBinding
import com.af.dentalla.ui.patient.homeScreen.Speciality
import com.af.dentalla.ui.patient.homeScreen.SpecialtyAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.selectTime.setOnClickListener {
            showTimePickerDialog()
        }
        setUpAddSpecialityRecyclerView()
    }
    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        // Create a date picker dialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set(Calendar.YEAR, year)
                selectedDateCalendar.set(Calendar.MONTH, month)
                selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Format the selected date
                val simpleDateFormat =
                    SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                val formattedDate = simpleDateFormat.format(selectedDateCalendar.time)

                // Display the selected date in the TextView
                // selectedDateTextView.text = formattedDate
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )

        // Show the date picker dialog
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTimeCalendar = Calendar.getInstance()
                selectedTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTimeCalendar.set(Calendar.MINUTE, minute)

                val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formattedTime = simpleDateFormat.format(selectedTimeCalendar.time)
                //selectedTimeTextView.text = formattedTime
            },
            hour,
            minute,
            false
        )

        timePickerDialog.show()
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
        val adapter = SpecialtyAdapter(specialtiesList) { specialityId ->
        }
        binding.specialityRecyclerView.adapter = adapter
        binding.specialityRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }
}


