package com.af.dentalla.ui.patient.doctorProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.usecase.patient.GetDoctorProfileUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorProfileViewModel @Inject constructor(
    private val getDoctorProfileUseCase: GetDoctorProfileUseCase
) : ViewModel() {
    private val _doctorProfile = MutableLiveData<ScreenState<DoctorProfileEntity>>()
    val doctorProfile: LiveData<ScreenState<DoctorProfileEntity>> get() = _doctorProfile

    fun getDoctorProfile(cardId: Int) {
        viewModelScope.launch {
            getDoctorProfileUseCase(cardId).collectLatest {
                when (it) {
                    is NetWorkResponseState.Loading -> _doctorProfile.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Error -> _doctorProfile.postValue(ScreenState.Error(it.exception.message!!))
                    is NetWorkResponseState.Success -> _doctorProfile.postValue(ScreenState.Success(it.result))
                }
            }
        }
    }
}