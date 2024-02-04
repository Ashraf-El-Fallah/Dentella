package com.af.dentalla.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.domain.entity.LoginResponseEntity
import com.af.dentalla.domain.user.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class LoginViewModel// @Inject constructor
    (
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginState = MutableLiveData<NetWorkResponseState<LoginResponseEntity>>()
    val loginState: LiveData<NetWorkResponseState<LoginResponseEntity>> get() = _loginState

    fun login(loginUser: LoginUser) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(loginUser).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _loginState.postValue(
                        NetWorkResponseState.Error(
                            Throwable(message = "Failed to fetch data")
                        )
                    )

                    is NetWorkResponseState.Loading -> _loginState.postValue(NetWorkResponseState.Loading)

                    is NetWorkResponseState.Success -> _loginState.postValue(
                        NetWorkResponseState.Success(it.result)
                    )

                }
            }

        }
    }

}