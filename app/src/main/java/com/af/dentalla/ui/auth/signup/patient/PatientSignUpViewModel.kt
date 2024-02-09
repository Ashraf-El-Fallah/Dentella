package com.af.dentalla.ui.auth.signup.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.usecase.authentication.ValidateEmailFieldUseCase
import com.af.dentalla.domain.usecase.authentication.ValidatePhoneNumberFieldUseCase
import com.af.dentalla.domain.usecase.authentication.ValidateUserNameFieldUseCase
import com.af.dentalla.domain.usecase.authentication.signup.SignUpPatientUseCase
import com.af.dentalla.domain.usecase.authentication.signup.ValidatePasswordAndConfirmation
import com.af.dentalla.ui.auth.signup.SignUpState
import com.af.dentalla.utilities.FormFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientSignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpPatientUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val validatePhoneNumberFieldUseCase: ValidatePhoneNumberFieldUseCase,
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePasswordAndConfirmation: ValidatePasswordAndConfirmation
) : ViewModel() {
    private val _signUpState = MutableLiveData<NetWorkResponseState<SignUpState>>()
    val signUpPatientState: LiveData<NetWorkResponseState<SignUpState>> get() = _signUpState

    suspend fun signUp(
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        val signUpState = signUpUseCase(username, email, phone, password)
        viewModelScope.launch {
            isSignUpValidate(username, email, phone, password, confirmPassword)
        }
        return signUpState
    }

    private fun isSignUpValidate(
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
    ) {
        isUsernameValid(username)
        isEmailValid(email)
        isPhoneNumberValid(phone)
        isPasswordValid(password, confirmPassword)
    }

    private fun isUsernameValid(userName: String) {
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

    private fun isEmailValid(email: String) {
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

    private fun isPhoneNumberValid(phoneNumber: String) {
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

    private fun isPasswordValid(password: String, confirmPassword: String) {
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
