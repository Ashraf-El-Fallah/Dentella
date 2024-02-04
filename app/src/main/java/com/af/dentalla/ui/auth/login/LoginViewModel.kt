package com.af.dentalla.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.domain.entity.UserResponseEntity
import com.af.dentalla.domain.repository.DoctorRepository
import com.af.dentalla.domain.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _loginState = MutableLiveData<NetWorkResponseState<UserResponseEntity>>()
    val loginState: LiveData<NetWorkResponseState<UserResponseEntity>> get() = _loginState

    fun login(loginUser: LoginUser) {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase(loginUser).collect {
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