package com.af.dentalla.ui.auth.signup.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.SignUpDoctor
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.domain.entity.SignUpEntity
import com.af.dentalla.domain.repository.PatientRepository
import com.af.dentalla.domain.usecase.patient.signup.SignUpPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientSignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpPatientUseCase
) : ViewModel() {
    private val _signUpState = MutableLiveData<NetWorkResponseState<SignUpEntity>>()
    val signUpPatientState: LiveData<NetWorkResponseState<SignUpEntity>> get() = _signUpState

    fun signUp(signUpPatient: SignUpPatient) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase(signUpPatient).collect {
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

}