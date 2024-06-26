package com.af.dentalla.domain.usecase.authentication

import android.app.Application
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.domain.usecase.authentication.validations.ValidateEmailFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidatePasswordFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidateUserNameFieldUseCase
import com.af.dentalla.utils.AccountManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase,
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val application: Application
) {
    fun execute(
        accountType: String,
        userName: String?,
        email: String?,
        password: String?,
        errorMessage: String
    ): Flow<NetWorkResponseState<Unit>> {
        return flow {
            if (accountType == AccountManager.AccountType.PATIENT.toString()) {
                if (validateUserNameFieldUseCase(userName.toString()) || validatePasswordFieldUseCase(
                        password.toString()
                    )
                ) {
                    emit(NetWorkResponseState.Error(Throwable(errorMessage)))
                    return@flow
                }
                val loginPatient =
                    LoginPatient(userName = userName.toString(), passWord = password.toString())
                repository.loginUser(loginPatient).collect { emit(it) }
            } else if (accountType == AccountManager.AccountType.DOCTOR.toString()) {
                if (validateEmailFieldUseCase(email.toString()) || validatePasswordFieldUseCase(
                        password.toString()
                    )
                ) {
                    emit(NetWorkResponseState.Error(Throwable(errorMessage)))
                    return@flow
                }
                val loginDoctor = LoginDoctor(email = email.toString(), passWord = password)
                repository.loginUser(loginDoctor).collect { emit(it) }
            }
        }
    }
}