package com.af.dentalla.ui.doctor.addCard

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.R
import com.af.dentalla.data.remote.dto.DoctorAvailability
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.databinding.FragmentAddCardBinding
import com.af.dentalla.ui.patient.homeScreen.Speciality
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.getSpecialtyName
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    private val addCardViewModel: AddCardViewModel by viewModels()
    private var selectedDate: Date? = null
    private var selectedTime: Date? = null
    private var specialityId = -1
    private var doctorAvailability: DoctorAvailability? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCardObserver()
        binding.selectTime.setOnClickListener {
            showTimePickerDialog()
        }
        setDateFromTheCalender()
        setUpAddSpecialityRecyclerView()
        binding.confirmButton.setOnClickListener {
            createFormatForDateAndTime()
            sendCardInformationToViewModel()
        }
    }

    private fun sendCardInformationToViewModel() {
        if ((doctorAvailability == null && specialityId == -1) || (doctorAvailability == null || specialityId == -1)) {
            Snackbar.make(
                requireView(),
                "You should choose free time,free date and your speciality",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            val formattedDateForUser =
                SimpleDateFormat("MMMM dd", Locale.getDefault()).format(selectedDate)
            val formattedTimeForUser =
                SimpleDateFormat("hh:mm a", Locale.getDefault()).format(selectedTime)
            Snackbar.make(
                requireView(),
                "Your speciality is ${getSpecialtyName(specialityId)} and your selected date is $formattedDateForUser at $formattedTimeForUser",
                Snackbar.LENGTH_LONG
            ).show()
            val card = Card(doctorAvailability, specialityId)
            addCardViewModel.addCard(card)
        }
    }



    private fun addCardObserver() {
        addCardViewModel.addArticleState.observe(viewLifecycleOwner) { addCardState ->
            when (addCardState) {
                is ScreenState.Loading -> {
                    binding.progressBar.root.visible()
                }

                is ScreenState.Error -> {
                    binding.progressBar.root.gone()
                }

                is ScreenState.Success -> {
                    binding.progressBar.root.gone()
                }
            }
        }
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
        if (selectedDate != null && selectedTime != null) {
            val formattedDate =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
            // Format the selected time
            val formattedTime =
                SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(selectedTime)

            val formattedDateTime = formattedDate + "T" + formattedTime + "Z"
            val availableDates: List<String?> = listOf(formattedDateTime)
            doctorAvailability = DoctorAvailability(availableDates)
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
            Speciality(R.drawable.add_dental_crown, 4, "Extraction"),
//            Speciality(R.drawable.dental_implant, 3, "Implants"),
//            Speciality(R.drawable.add_tooth_extraction, 2, "Crowns"),
//            Speciality(R.drawable.denture, 5, "Orthodontic"),
        )
        val adapter = AddSpecialityAdapter(specialtiesList) { specialtyIdChosen ->
            specialityId = specialtyIdChosen
        }
        binding.specialityRecyclerView.adapter = adapter
        binding.specialityRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter.setSelectedItem(RecyclerView.NO_POSITION)
    }
}



