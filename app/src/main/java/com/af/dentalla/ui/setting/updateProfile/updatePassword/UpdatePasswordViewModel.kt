package com.af.dentalla.ui.setting.updateProfile.updatePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.usecase.setting.UpdatePasswordUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(private val updatePasswordUseCase: UpdatePasswordUseCase) :
    ViewModel() {
    private val _updateUserPasswordState = MutableLiveData<ScreenState<Unit>>()
    val updateUserPasswordState: LiveData<ScreenState<Unit>> get() = _updateUserPasswordState

    fun changeDoctorPassword(
        oldPassword: String,
        newPassword: String
    ) {
        viewModelScope.launch {
            updatePasswordUseCase.execute(oldPassword = oldPassword, newPassword = newPassword)
                .collect {
                    when (it) {
                        is NetWorkResponseState.Loading -> _updateUserPasswordState.postValue(
                            ScreenState.Loading
                        )

                        is NetWorkResponseState.Success -> _updateUserPasswordState.postValue(
                            ScreenState.Success(it.result)
                        )

                        is NetWorkResponseState.Error -> _updateUserPasswordState.postValue(
                            ScreenState.Error(
                                errorMessageCode = it.errorMessageResId,
                                statusCode = it.statusCode
                            )
                        )
                    }
                }
        }
    }
}