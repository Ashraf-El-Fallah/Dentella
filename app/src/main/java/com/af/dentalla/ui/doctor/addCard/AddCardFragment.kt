package com.af.dentalla.ui.doctor.addCard

import android.app.TimePickerDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.R
import com.af.dentalla.data.remote.dto.DoctorAvailability
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.databinding.FragmentAddCardBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.getSpecialtyName
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
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

    override fun onAttach(context: Context) {
        super.onAttach(updateLocale(context, Locale("en")))
    }

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
        setLocaleForCalendarView()
    }

    private fun updateLocale(context: Context, locale: Locale): ContextWrapper {
        val config = context.resources.configuration
        config.setLocale(locale)
        return ContextWrapper(context.createConfigurationContext(config))
    }

    private fun setLocaleForCalendarView() {
        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        binding.calendarView.date = Calendar.getInstance(locale).timeInMillis
    }

    private fun sendCardInformationToViewModel() {
        if ((doctorAvailability == null && specialityId == -1) || (doctorAvailability == null || specialityId == -1)) {
            Snackbar.make(
                requireView(),
                R.string.choose_free_time_and_speciality,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            val formattedDateForUser =
                selectedDate?.let { SimpleDateFormat("MMMM dd", Locale.getDefault()).format(it) }
            val formattedTimeForUser =
                selectedTime?.let { SimpleDateFormat("hh:mm a", Locale.getDefault()).format(it) }
            val toastMessage =
                "${getString(R.string.your_speciality_is)} ${
                    getSpecialtyName(
                        context,
                        specialityId
                    )
                } ${
                    getString(R.string.your_selected_date_is)
                } $formattedDateForUser ${getString(R.string.at)} $formattedTimeForUser"
            Snackbar.make(
                requireView(),
                toastMessage,
                Snackbar.LENGTH_LONG
            ).show()
            val card = Card(doctorAvailability, specialityId)
            addCardViewModel.addCard(card)
        }
    }


    private fun addCardObserver() {
        addCardViewModel.addCardState.observe(viewLifecycleOwner) { addCardState ->
            when (addCardState) {
                is ScreenState.Loading -> {
                    binding.progressBar.root.visible()
                }

                is ScreenState.Error -> {
                    binding.progressBar.root.gone()
                    if (addCardState.message == "HTTP 401 Unauthorized") {
                        Toast.makeText(
                            requireContext(),
                            R.string.want_to_login_again,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.cannot_send_data,
                            Toast.LENGTH_LONG
                        ).show()
                    }
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
            val currentDate = Calendar.getInstance().time
            if (selectedDate!!.before(currentDate)) {
                return
            }

            val formattedDate =
                selectedDate?.let { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it) }
            val formattedTime =
                selectedTime?.let {
                    SimpleDateFormat(
                        "HH:mm:ss.SSS",
                        Locale.getDefault()
                    ).format(it)
                }

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
            SpecialityAddCard(
                R.drawable.cleaning_black,
                R.drawable.cleaning_white,
                0,
                getString(R.string.cleaning)
            ),
            SpecialityAddCard(
                R.drawable.filling_black,
                R.drawable.filling_white,
                1,
                getString(R.string.filling)
            ),
            SpecialityAddCard(
                R.drawable.extraction_black,
                R.drawable.extraction_white,
                4,
                getString(R.string.extraction)
            ),
            SpecialityAddCard(
                R.drawable.implants_black,
                R.drawable.implants_white,
                3,
                getString(R.string.implants)
            ),
            SpecialityAddCard(
                R.drawable.crowns_black,
                R.drawable.crowns_white,
                2,
                getString(R.string.crowns)
            ),
            SpecialityAddCard(
                R.drawable.denture_black,
                R.drawable.denture_white,
                5,
                getString(R.string.dentures)
            )
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



