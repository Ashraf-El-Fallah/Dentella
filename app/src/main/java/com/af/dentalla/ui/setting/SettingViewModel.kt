package com.af.dentalla.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.usecase.authentication.LogoutUseCase
import com.af.dentalla.domain.usecase.setting.DeleteUserInfoUseCase
import com.af.dentalla.utils.Event
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase
) : ViewModel() {
    private val _logoutState = MutableLiveData<Event<ScreenState<Unit>>>()
    val logoutState: LiveData<Event<ScreenState<Unit>>> get() = _logoutState

    private val _deleteInfoState = MutableLiveData<Event<ScreenState<Unit>>>()
    val deleteInfoState: LiveData<Event<ScreenState<Unit>>> get() = _deleteInfoState

    fun logout() {
        viewModelScope.launch {
            logoutUseCase().collect { logoutResponse ->
                when (logoutResponse) {
                    is NetWorkResponseState.Loading -> _logoutState.postValue(Event(ScreenState.Loading))
                    is NetWorkResponseState.Success -> _logoutState.postValue(
                        Event(
                            ScreenState.Success(
                                logoutResponse.result
                            )
                        )
                    )

                    is NetWorkResponseState.Error -> _logoutState.postValue(
                        Event(
                            ScreenState.Error(
                                message = logoutResponse.exception.message.toString()
                            )
                        )
                    )
                }
            }
        }
    }

    fun deleteUserInfo() {
        viewModelScope.launch {
            deleteUserInfoUseCase().collect { deleteUserInfoResponse ->
                when (deleteUserInfoResponse) {
                    is NetWorkResponseState.Loading -> _deleteInfoState.postValue(Event(ScreenState.Loading))
                    is NetWorkResponseState.Success -> _deleteInfoState.postValue(
                        Event(
                            ScreenState.Success(
                                deleteUserInfoResponse.result
                            )
                        )
                    )

                    is NetWorkResponseState.Error -> _deleteInfoState.postValue(
                        Event(
                            ScreenState.Error(
                                message = deleteUserInfoResponse.exception.message.toString()
                            )
                        )
                    )
                }
            }
        }
    }
}