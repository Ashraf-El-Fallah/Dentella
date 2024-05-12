package com.af.dentalla.ui.setting.updateProfile.updatePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.DoctorPassword
import com.af.dentalla.domain.usecase.doctor.ChangeDoctorPasswordUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(private val changeDoctorPasswordUseCase: ChangeDoctorPasswordUseCase) :
    ViewModel() {
    private val _changeDoctorPasswordState = MutableLiveData<ScreenState<Unit>>()
    val changeDoctorPasswordState: LiveData<ScreenState<Unit>> get() = _changeDoctorPasswordState

    fun changeDoctorPassword(doctorPassword: DoctorPassword) {
        viewModelScope.launch {
            changeDoctorPasswordUseCase(doctorPassword).collect {
                when (it) {
                    is NetWorkResponseState.Loading -> _changeDoctorPasswordState.postValue(
                        ScreenState.Loading
                    )

                    is NetWorkResponseState.Success -> _changeDoctorPasswordState.postValue(
                        ScreenState.Success(it.result)
                    )

                    is NetWorkResponseState.Error -> _changeDoctorPasswordState.postValue(
                        ScreenState.Error(it.exception.message.toString())
                    )
                }
            }
        }
    }
}