package com.af.dentalla.ui.setting.updateProfile.updatePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.UserPasswords
import com.af.dentalla.domain.usecase.doctor.ChangeUserPasswordUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeUserPasswordViewModel @Inject constructor(private val changeUserPasswordUseCase: ChangeUserPasswordUseCase) :
    ViewModel() {
    private val _changeUserPasswordState = MutableLiveData<ScreenState<Unit>>()
    val changeUserPasswordState: LiveData<ScreenState<Unit>> get() = _changeUserPasswordState

    fun changeDoctorPassword(userPasswords: UserPasswords) {
        viewModelScope.launch {
            changeUserPasswordUseCase(userPasswords).collect {
                when (it) {
                    is NetWorkResponseState.Loading -> _changeUserPasswordState.postValue(
                        ScreenState.Loading
                    )

                    is NetWorkResponseState.Success -> _changeUserPasswordState.postValue(
                        ScreenState.Success(it.result)
                    )

                    is NetWorkResponseState.Error -> _changeUserPasswordState.postValue(
                        ScreenState.Error(it.exception.message.toString())
                    )
                }
            }
        }
    }
}