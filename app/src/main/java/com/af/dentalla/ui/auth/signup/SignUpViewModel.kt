package com.af.dentalla.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.usecase.authentication.signup.SignUpUserUseCase
import com.af.dentalla.utilities.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase
) : ViewModel() {
    private val _signUpState = MutableLiveData<ScreenState<Unit>>()
    val signUpDoctorState: LiveData<ScreenState<Unit>> get() = _signUpState

    fun signUpUserLogic(signUpUser: SignUpUser) {
        viewModelScope.launch {
            signUpUserUseCase(signUpUser).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _signUpState.postValue(ScreenState.Error(it.exception.message.toString()))
                    is NetWorkResponseState.Loading -> _signUpState.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _signUpState.postValue(
                        ScreenState.Success(Unit)
                    )
                }
            }
        }
    }


//    fun signUpPatientLogic(signUpPatient: SignUpUser) {
//        viewModelScope.launch {
//            signUpPatientUseCase(signUpPatient).collect {
//                when (it) {
//                    is NetWorkResponseState.Error -> _signUpState.postValue(ScreenState.Error(it.exception.message.toString()))
//                    is NetWorkResponseState.Loading -> _signUpState.postValue(ScreenState.Loading)
//                    is NetWorkResponseState.Success -> _signUpState.postValue(
//                        ScreenState.Success(
//                            Unit
//                        )
//                    )
//                }
//            }
//        }
//    }
}