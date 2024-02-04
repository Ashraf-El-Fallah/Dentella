package com.af.dentalla.ui.auth.signup

import android.app.PendingIntent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.SignUpUser
import com.af.dentalla.domain.entity.SignUpResponseEntity
import com.af.dentalla.domain.user.signup.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class CreateAccountViewModel //@Inject constructor
    (
    private val signUseCase: SignUpUseCase
) : ViewModel() {
    private val _signUpState = MutableLiveData<NetWorkResponseState<SignUpResponseEntity>>()
    val signUpState: LiveData<NetWorkResponseState<SignUpResponseEntity>> get() = _signUpState

    fun signUp(signUpUser: SignUpUser) {
        viewModelScope.launch(Dispatchers.IO) {
            signUseCase(signUpUser).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _signUpState.postValue(
                        NetWorkResponseState.Error(
                            Throwable(message = "Failed To fetch data")
                        )
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
}