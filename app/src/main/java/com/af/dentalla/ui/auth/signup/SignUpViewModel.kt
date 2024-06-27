package com.af.dentalla.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.usecase.authentication.SignUpUserUseCase
import com.af.dentalla.utils.Event
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase
) : ViewModel() {
    private val _signUpState = MutableLiveData<Event<ScreenState<Unit>>>()
    val signUpDoctorState: LiveData<Event<ScreenState<Unit>>> get() = _signUpState

    fun signUpUserLogic(
        accountType: String,
        userName: String?,
        email: String?,
        phone: String?,
        password: String?,
        confirmPassword: String?,
        id: String? = null
    ) {
        viewModelScope.launch {
            signUpUserUseCase.execute(
                accountType,
                userName,
                email,
                phone,
                password,
                confirmPassword,
                id
            ).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _signUpState.postValue(
                        Event(
                            ScreenState.Error(
                                message = it.exception.message.toString(),
                                errorMessageCode = it.errorMessageResId
                            )
                        )
                    )

                    is NetWorkResponseState.Loading -> _signUpState.postValue(Event(ScreenState.Loading))
                    is NetWorkResponseState.Success -> _signUpState.postValue(
                        Event(
                            ScreenState.Success(
                                Unit
                            )
                        )
                    )
                }
            }
        }
    }
}