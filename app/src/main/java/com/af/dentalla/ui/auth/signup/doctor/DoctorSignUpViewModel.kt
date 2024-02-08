package com.af.dentalla.ui.auth.signup.doctor

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.usecase.doctor.signup.SignUpDoctorUseCase
import com.af.dentalla.ui.auth.SignUpResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorSignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpDoctorUseCase,
) : ViewModel(){
    private val _signUpState = MutableLiveData<NetWorkResponseState<SignUpEntity>>()
    val signUpDoctorState: LiveData<NetWorkResponseState<SignUpEntity>> get() = _signUpState

    fun isSignUpValidate(
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
        id: String
    ) {
        when {
            !isUsernameValid(username) -> SignUpResult.SignUpError("Invalid username!")
            !isPasswordValid(password) -> SignUpResult.SignUpError("Invalid password!")
            !arePasswordsTheSame(
                password, confirmPassword
            ) -> SignUpResult.SignUpError("Passwords are not matched!")

            !isEmailValid(email) -> SignUpResult.SignUpError("Invalid Email")
            !isPhoneValid(phone) -> SignUpResult.SignUpError("Invalid Phone number")
            !isIdValid(id) -> SignUpResult.SignUpError("Invalid Id")
            else -> {
                val signUpDoctor = SignUpDoctor(email, phone, password, phone, id)
                signUp(signUpDoctor)
            }
        }

    }

    private fun signUp(signUpDoctor: SignUpDoctor) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase(signUpDoctor).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _signUpState.postValue(
                        NetWorkResponseState.Error(it.exception)
                    )

                    is NetWorkResponseState.Loading -> _signUpState.postValue(
                        NetWorkResponseState.Loading
                    )

                    is NetWorkResponseState.Success -> _signUpState.postValue(
                        NetWorkResponseState.Success(it.result)
                    )
                }
            }

        }
    }

    private fun isUsernameValid(username: String) = username.length > 3 && username.isNotBlank()

    private fun isPasswordValid(password: String) = password.length > 8

    private fun isPhoneValid(phone: String) = phone.length == 11

    private fun arePasswordsTheSame(password: String, confirmationPassword: String) =
        password == confirmationPassword

    private fun isEmailValid(email: String) =
        email.isNotEmpty() && email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isIdValid(id: String) =
        id.isNotEmpty() && id.length > 5

}