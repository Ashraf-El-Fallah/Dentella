package com.af.dentalla.ui.auth.login.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.domain.usecase.authentication.login.LoginPatientUseCase
import com.af.dentalla.domain.usecase.authentication.login.ValidateFieldUseCase
import com.af.dentalla.domain.usecase.authentication.ValidateUserNameFieldUseCase
import com.af.dentalla.domain.usecase.authentication.login.ValidatePasswordFieldUseCase
import com.af.dentalla.ui.Event
import com.af.dentalla.ui.auth.login.LoginUIEvent
import com.af.dentalla.ui.auth.login.LoginUiState
import com.af.dentalla.utilities.AccountManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginPatientViewModel @Inject constructor(
    private val loginPatientUseCase: LoginPatientUseCase,
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase,
    private val validateFieldUseCase: ValidateFieldUseCase
) : ViewModel() {
    private val accountType = AccountManager.accountType

    private val _loginUIState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUIState.asStateFlow()

    private val _loginEvent = MutableStateFlow<Event<LoginUIEvent?>>(Event(null))
    val loginEvent = _loginEvent.asStateFlow()

    fun onClickSignUp() {
        _loginEvent.update { Event(LoginUIEvent.SignUpEvent(accountType)) }
    }

    fun onClickLogin() {
        login()
    }

    fun onUserInputChanged(userName: CharSequence) {
        val userNameFieldState = validateUserNameFieldUseCase(userName.toString())
        _loginUIState.update {
            it.copy(
                userName = userName.toString(),
                userNameHelperText = userNameFieldState.errorMessage() ?: "",
                isValidForm = validateFieldUseCase(
                    loginUiState.value.userName,
                    loginUiState.value.password
                )
            )
        }
    }

    fun onPasswordInputChanged(password: CharSequence) {
        val passwordFieldState =
            validatePasswordFieldUseCase(password.toString())
        _loginUIState.update {
            it.copy(
                password = password.toString(),
                passwordHelperText = passwordFieldState.errorMessage() ?: "",
                isValidForm = validateFieldUseCase(
                    loginUiState.value.userName,
                    loginUiState.value.password
                )
            )
        }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                _loginUIState.update { it.copy(isLoading = true) }
                val loginState =
                    loginPatientUseCase(
                        loginUiState.value.userName,
                        loginUiState.value.password
                    )
                if (loginState) {
                    onLoginSuccessfully()
                }
            } catch (e: Throwable) {
                onLoginError(e.message.toString())
            }
        }
    }

    private fun onLoginSuccessfully() {
        _loginUIState.update { it.copy(isLoading = false) }
        _loginEvent.update { Event(LoginUIEvent.LoginEvent(accountType)) }
        resetForm()
    }

    private fun resetForm() {
        _loginUIState.update { it.copy(userName = "", password = "") }
    }

    private fun onLoginError(message: String) {
        _loginUIState.update {
            it.copy(
                isLoading = false,
                error = message,
                passwordHelperText = message
            )
        }
    }

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

}