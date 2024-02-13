package com.af.dentalla.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.domain.usecase.authentication.ValidateEmailFieldUseCase
import com.af.dentalla.domain.usecase.authentication.login.LoginPatientUseCase
import com.af.dentalla.domain.usecase.authentication.login.ValidateFieldPatientUseCase
import com.af.dentalla.domain.usecase.authentication.ValidateUserNameFieldUseCase
import com.af.dentalla.domain.usecase.authentication.login.LoginDoctorUseCase
import com.af.dentalla.domain.usecase.authentication.login.ValidateFieldDoctorUseCase
import com.af.dentalla.domain.usecase.authentication.login.ValidatePasswordFieldUseCase
import com.af.dentalla.utilities.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginPatientUseCase: LoginPatientUseCase,
    private val loginDoctorUseCase: LoginDoctorUseCase,
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase,
    private val validateFieldPatientUseCase: ValidateFieldPatientUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val validateFieldDoctorUseCase: ValidateFieldDoctorUseCase
) : ViewModel() {
    private val _loginState = MutableLiveData<ScreenState<Unit>>()
    val loginState: LiveData<ScreenState<Unit>> get() = _loginState

    fun loginPatientLogic(loginPatient: LoginPatient) {
        viewModelScope.launch {
            loginPatientUseCase(loginPatient).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _loginState.postValue(ScreenState.Error(it.exception.message.toString()))
                    is NetWorkResponseState.Loading -> _loginState.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _loginState.postValue(
                        ScreenState.Success(Unit)
                    )
                }
            }
        }
    }

    fun loginDoctorLogic(loginDoctor: LoginDoctor) {
        viewModelScope.launch {
            loginDoctorUseCase(loginDoctor).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _loginState.postValue(ScreenState.Error(it.exception.message.toString()))
                    is NetWorkResponseState.Loading -> _loginState.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _loginState.postValue(
                        ScreenState.Success(
                            Unit
                        )
                    )
                }
            }
        }
    }


}

///////ui state////////////////////////////////////////////////////////////////////////

//private val _loginUIState = MutableStateFlow(LoginUiState())
//    val loginUiState = _loginUIState.asStateFlow()
//
//    private val _loginEvent = MutableStateFlow<Event<LoginUIEvent?>>(Event(null))
//    val loginEvent = _loginEvent.asStateFlow()
//
//    fun onClickSignUp() {
//        _loginEvent.update { Event(LoginUIEvent.SignUpEvent(accountType)) }
//    }
//
//    fun onClickLoginForPatient() {
//        loginForPatient()
//    }
//
//    //doctor
//    fun onClickLoginForDoctor() {
//        loginForDoctor()
//    }
//
//    fun onEmailInputChanged(email: CharSequence) {
//        val emailFieldState = validateEmailFieldUseCase(email.toString())
//        _loginUIState.update {
//            it.copy(
//                email = email.toString(),
//                emailHelperText = emailFieldState.errorMessage() ?: "",
//                isValidForm = validateFieldDoctorUseCase(
//                    loginUiState.value.email,
//                    loginUiState.value.password
//                )
//            )
//        }
//    }
//
//    fun onPasswordInputChangedForDoctor(password: CharSequence) {
//        val passwordFieldState =
//            validatePasswordFieldUseCase(password.toString())
//        _loginUIState.update {
//            it.copy(
//                password = password.toString(),
//                passwordHelperText = passwordFieldState.errorMessage() ?: "",
//                isValidForm = validateFieldDoctorUseCase(
//                    loginUiState.value.email,
//                    loginUiState.value.password
//                )
//            )
//        }
//    }
//
//    private fun loginForDoctor() {
//        viewModelScope.launch {
//            try {
//                _loginUIState.update { it.copy(isLoading = true) }
//                val loginState =
//                    loginPatientUseCase(
//                        loginUiState.value.userName,
//                        loginUiState.value.password
//                    )
//                if (loginState) {
//                    onLoginSuccessfully()
//                    resetFormDoctor()
//                }
//            } catch (e: Throwable) {
//                onLoginError(e.message.toString())
//            }
//        }
//    }
//
//    private fun resetFormDoctor() {
//        _loginUIState.update { it.copy(email = "", password = "") }
//    }
//
//
//    //patient//
//
//    fun onUserNameInputChanged(userName: CharSequence) {
//        val userNameFieldState = validateUserNameFieldUseCase(userName.toString())
//        _loginUIState.update {
//            it.copy(
//                userName = userName.toString(),
//                userNameHelperText = userNameFieldState.errorMessage() ?: "",
//                isValidForm = validateFieldPatientUseCase(
//                    loginUiState.value.userName,
//                    loginUiState.value.password
//                )
//            )
//        }
//    }
//
//    fun onPasswordInputChangedForPatient(password: CharSequence) {
//        val passwordFieldState =
//            validatePasswordFieldUseCase(password.toString())
//        _loginUIState.update {
//            it.copy(
//                password = password.toString(),
//                passwordHelperText = passwordFieldState.errorMessage() ?: "",
//                isValidForm = validateFieldPatientUseCase(
//                    loginUiState.value.userName,
//                    loginUiState.value.password
//                )
//            )
//        }
//    }
//
//    private fun loginForPatient() {
//        viewModelScope.launch {
//            try {
//                _loginUIState.update { it.copy(isLoading = true) }
//                val loginState =
//                    loginDoctorUseCase(
//                        loginUiState.value.email,
//                        loginUiState.value.password
//                    )
//                if (loginState) {
//                    onLoginSuccessfully()
//                    resetFormPatient()
//                }
//            } catch (e: Throwable) {
//                onLoginError(e.message.toString())
//            }
//        }
//    }
//
//    private fun resetFormPatient() {
//        _loginUIState.update { it.copy(userName = "", password = "") }
//    }
//
//
//    private fun onLoginError(message: String) {
//        _loginUIState.update {
//            it.copy(
//                isLoading = false,
//                error = message,
//                passwordHelperText = message
//            )
//        }
//    }
//
//    private fun onLoginSuccessfully() {
//        _loginUIState.update { it.copy(isLoading = false) }
//        _loginEvent.update { Event(LoginUIEvent.LoginEvent(accountType)) }
////        resetFormPatient()
//    }


////////////////////////////////////////////////////////////////////////////////////////

//    fun login(loginUser: LoginUser) {
//        viewModelScope.launch(Dispatchers.IO) {
//            loginPatientUseCase(loginUser).collect {
//                when (it) {
//                    is NetWorkResponseState.Error -> _loginUIState.postValue(
//                        NetWorkResponseState.Error(
//                            it.exception
//                        )
//                    )
//
//                    is NetWorkResponseState.Loading -> _loginUIState.postValue(NetWorkResponseState.Loading)
//
//                    is NetWorkResponseState.Success -> _loginUIState.postValue(
//                        NetWorkResponseState.Success(it.result)
//                    )
//
//                }
//            }
//
//        }
//    }
