package com.af.dentalla.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.usecase.login.LoginPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginPatientUseCase: LoginPatientUseCase
) : ViewModel() {
    private val _loginState = MutableLiveData<NetWorkResponseState<LoginEntity>>()
    val loginState: LiveData<NetWorkResponseState<LoginEntity>> get() = _loginState

    fun login(loginUser: LoginUser) {
        viewModelScope.launch(Dispatchers.IO) {
            loginPatientUseCase(loginUser).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _loginState.postValue(
                        NetWorkResponseState.Error(
                            it.exception
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