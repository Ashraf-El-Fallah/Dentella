package com.af.dentalla.ui.setting.updateProfile.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.DoctorProfileInformation
import com.af.dentalla.domain.entity.ProfileInformationEntity
import com.af.dentalla.domain.usecase.doctor.GetDoctorProfileInformationUseCase
import com.af.dentalla.domain.usecase.doctor.UpdateDoctorProfileInformationUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getDoctorProfileInformationUseCase: GetDoctorProfileInformationUseCase,
    private val updateDoctorProfileInformationUseCase: UpdateDoctorProfileInformationUseCase
) : ViewModel() {
    private val _profileInformation = MutableLiveData<ScreenState<ProfileInformationEntity>>()
    val profileInformation: LiveData<ScreenState<ProfileInformationEntity>> get() = _profileInformation

    private val _updateDoctorProfileInFormation = MutableLiveData<ScreenState<Unit>>()
    val updateDoctorProfileInFormation: LiveData<ScreenState<Unit>> get() = _updateDoctorProfileInFormation


    init {
        getProfileInformation()
    }

    private fun getProfileInformation() {
        viewModelScope.launch {
            getDoctorProfileInformationUseCase().collectLatest {
                when (it) {
                    is NetWorkResponseState.Loading -> _profileInformation.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _profileInformation.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )

                    is NetWorkResponseState.Error -> _profileInformation.postValue(
                        ScreenState.Error(
                            it.exception.message.toString()
                        )
                    )
                }
            }
        }
    }

    fun updateDoctorProfileInformation(doctorProfileInformation: DoctorProfileInformation) {
        viewModelScope.launch {
            updateDoctorProfileInformationUseCase(doctorProfileInformation).collect {
                when (it) {
                    is NetWorkResponseState.Loading -> _updateDoctorProfileInFormation.postValue(
                        ScreenState.Loading
                    )

                    is NetWorkResponseState.Success -> _updateDoctorProfileInFormation.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )

                    is NetWorkResponseState.Error -> _updateDoctorProfileInFormation.postValue(
                        ScreenState.Error(
                            it.exception.message.toString()
                        )
                    )
                }
            }
        }
    }
}