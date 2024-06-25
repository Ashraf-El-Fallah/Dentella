package com.af.dentalla.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.usecase.authentication.login.LoginUserUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val _loginState = MutableLiveData<ScreenState<Unit>>()
    val loginState: LiveData<ScreenState<Unit>> get() = _loginState

    fun loginUserLogic(
        accountType: String,
        userName: String?,
        email: String?,
        password: String?,
        errorMessage: String
    ) {
        viewModelScope.launch {
            loginUserUseCase.execute(accountType, userName, email, password, errorMessage).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _loginState.postValue(ScreenState.Error(it.exception.message.toString()))
                    is NetWorkResponseState.Loading -> _loginState.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success<*> -> _loginState.postValue(
                        ScreenState.Success(
                            Unit
                        )
                    )
                }
            }
        }
    }
}