package com.af.dentalla.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.usecase.authentication.LogoutUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    private val _logoutState = MutableLiveData<ScreenState<Unit>>()
    val logoutState: LiveData<ScreenState<Unit>> get() = _logoutState

    fun logout() {
        viewModelScope.launch {
            logoutUseCase().collect { logoutResponse ->
                when (logoutResponse) {
                    is NetWorkResponseState.Loading -> _logoutState.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _logoutState.postValue(
                        ScreenState.Success(
                            logoutResponse.result
                        )
                    )

                    is NetWorkResponseState.Error -> _logoutState.postValue(
                        ScreenState.Error(
                            message = logoutResponse.exception.message.toString()
                        )
                    )
                }
            }
        }
    }
}