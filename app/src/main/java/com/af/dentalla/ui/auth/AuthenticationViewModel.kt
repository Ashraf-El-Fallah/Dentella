package com.af.dentalla.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.domain.usecase.authentication.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {
    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> get() = _isUserLoggedIn

    init {
        checkIfUserLoggedIn()
    }
    private fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            getTokenUseCase().collect { token ->
                _isUserLoggedIn.value = !token.isNullOrEmpty()
            }
        }
    }
}