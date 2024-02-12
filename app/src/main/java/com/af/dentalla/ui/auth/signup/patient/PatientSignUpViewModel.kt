package com.af.dentalla.ui.auth.signup.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.domain.usecase.authentication.ValidateEmailFieldUseCase
import com.af.dentalla.domain.usecase.authentication.ValidatePhoneNumberFieldUseCase
import com.af.dentalla.domain.usecase.authentication.ValidateUserNameFieldUseCase
import com.af.dentalla.domain.usecase.authentication.signup.SignUpPatientUseCase
import com.af.dentalla.domain.usecase.authentication.signup.ValidatePasswordAndConfirmation
import com.af.dentalla.ui.auth.signup.SignUpState
import com.af.dentalla.utilities.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientSignUpViewModel @Inject constructor(
    private val signUpPatientUseCase: SignUpPatientUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val validatePhoneNumberFieldUseCase: ValidatePhoneNumberFieldUseCase,
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePasswordAndConfirmation: ValidatePasswordAndConfirmation,
) : ViewModel() {
    private val _signUpState = MutableLiveData<ScreenState<SignUpState>>()
    val signUpPatientState: LiveData<ScreenState<SignUpState>> get() = _signUpState

//    fun signUpForPatient(
//        username: String,
//        email: String,
//        phone: String,
//        password: String,
//        confirmPassword: String,
//    ): Boolean {
//        var signUpState = false
//        viewModelScope.launch {
//            signUpState = signUpPatientUseCase(username, email, phone, password)
//            isSignUpValidateForPatient(username, email, phone, password, confirmPassword)
//        }
//        return signUpState
//    }


    fun signUpForPatient(signUpPatient: SignUpPatient) {
        viewModelScope.launch {
            signUpPatientUseCase(signUpPatient).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _signUpState.postValue(ScreenState.Error(it.exception.message.toString()))
                    is NetWorkResponseState.Loading -> _signUpState.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _signUpState.postValue(ScreenState.Success(it.result))
                }
            }
        }
    }

    private fun isSignUpValidateForPatient(
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
    ): SignUpPatient {
        isUsernameValid(username)
        isEmailValid(email)
        isPhoneNumberValid(phone)
        isPasswordValid(password, confirmPassword)
        return SignUpPatient(username, email, password, phone)
    }

    private fun isUsernameValid(userName: String) {
        viewModelScope.launch {
            val fieldState = validateUserNameFieldUseCase(userName)
            val newState = when (val currentState =
                _signUpState.value ?: NetWorkResponseState.Success(SignUpState())) {
                is NetWorkResponseState.Loading -> currentState
                is NetWorkResponseState.Success -> {
                    val currentSignUpState = currentState.result
                    val updatedSignUpState = currentSignUpState.copy(userNameState = fieldState)
                    NetWorkResponseState.Success(updatedSignUpState)
                }

                is NetWorkResponseState.Error -> currentState
            }
            _signUpState.value = newState
        }
    }

    private fun isEmailValid(email: String) {
        viewModelScope.launch {

            val fieldState = validateEmailFieldUseCase(email)
            val newState = when (val currentState =
                _signUpState.value ?: NetWorkResponseState.Success(SignUpState())) {
                is NetWorkResponseState.Loading -> currentState
                is NetWorkResponseState.Success -> {
                    val currentSignUpState = currentState.result
                    val updatedSignUpState = currentSignUpState.copy(emailState = fieldState)
                    NetWorkResponseState.Success(updatedSignUpState)
                }

                is NetWorkResponseState.Error -> currentState
            }
            _signUpState.value = newState
        }
    }

    private fun isPhoneNumberValid(phoneNumber: String) {
        viewModelScope.launch {

            val fieldState = validatePhoneNumberFieldUseCase(phoneNumber)
            val newState = when (val currentState =
                _signUpState.value ?: NetWorkResponseState.Success(SignUpState())) {
                is NetWorkResponseState.Loading -> currentState
                is NetWorkResponseState.Success -> {
                    val currentSignUpState = currentState.result
                    val updatedSignUpState = currentSignUpState.copy(phoneNumberState = fieldState)
                    NetWorkResponseState.Success(updatedSignUpState)
                }

                is NetWorkResponseState.Error -> currentState
            }
            _signUpState.value = newState
        }
    }

    private fun isPasswordValid(password: String, confirmPassword: String) {
        viewModelScope.launch {

            val fieldState = validatePasswordAndConfirmation(password, confirmPassword)
            val newState = when (val currentState =
                _signUpState.value ?: NetWorkResponseState.Success(SignUpState())) {
                is NetWorkResponseState.Loading -> currentState
                is NetWorkResponseState.Success -> {
                    val currentSignUpState = currentState.result
                    val updatedSignUpState = currentSignUpState.copy(passwordState = fieldState)
                    NetWorkResponseState.Success(updatedSignUpState)
                }

                is NetWorkResponseState.Error -> currentState
            }
            _signUpState.value = newState
        }
    }
}

//    fun signUp(signUpPatient: SignUpPatient) {
//        viewModelScope.launch(Dispatchers.IO) {
//            signUpUseCase(signUpPatient).collect {
//                when (it) {
//                    is NetWorkResponseState.Error -> _signUpState.postValue(
//                        NetWorkResponseState.Error(it.exception)
//                    )
//
//                    is NetWorkResponseState.Loading -> _signUpState.postValue(
//                        NetWorkResponseState.Loading
//                    )
//
//                    is NetWorkResponseState.Success -> _signUpState.postValue(
//                        NetWorkResponseState.Success(it.result)
//                    )
//                }
//            }
//
//        }
//    }
