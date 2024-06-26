package com.af.dentalla.ui.setting.updateProfile.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.UserProfileInformation
import com.af.dentalla.domain.entity.ProfileInformationEntity
import com.af.dentalla.domain.usecase.doctor.GetUserProfileInformationUseCase
import com.af.dentalla.domain.usecase.setting.UpdateUserProfileInformationUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserProfileInformationUseCase: GetUserProfileInformationUseCase,
    private val updateUserProfileInformationUseCase: UpdateUserProfileInformationUseCase
) : ViewModel() {
    private val _profileInformation = MutableLiveData<ScreenState<ProfileInformationEntity>>()
    val profileInformation: LiveData<ScreenState<ProfileInformationEntity>> get() = _profileInformation

    private val _updateUserProfileInFormation = MutableLiveData<ScreenState<Unit>>()
    val updateUserProfileInFormation: LiveData<ScreenState<Unit>> get() = _updateUserProfileInFormation


    init {
        getUserProfileInformation()
    }

    private fun getUserProfileInformation() {
        viewModelScope.launch {
            getUserProfileInformationUseCase().collectLatest {
                when (it) {
                    is NetWorkResponseState.Loading -> _profileInformation.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _profileInformation.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )

                    is NetWorkResponseState.Error -> _profileInformation.postValue(
                        ScreenState.Error(
                            message = it.exception.message.toString()
                        )
                    )
                }
            }
        }
    }

    fun updateUserProfileInformation(userProfileInformation: UserProfileInformation) {
        viewModelScope.launch {
            updateUserProfileInformationUseCase(userProfileInformation).collect {
                when (it) {
                    is NetWorkResponseState.Loading -> _updateUserProfileInFormation.postValue(
                        ScreenState.Loading
                    )

                    is NetWorkResponseState.Success -> _updateUserProfileInFormation.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )

                    is NetWorkResponseState.Error -> _updateUserProfileInFormation.postValue(
                        ScreenState.Error(
                            message = it.exception.message.toString()
                        )
                    )
                }
            }
        }
    }
}