package com.af.dentalla.domain.usecase.authentication.login

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ValidationUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: UserRepository
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
                if (userName == null || !ValidationUtils.isUserNameValid(userName) || !ValidationUtils.isPasswordValid(
                        password.toString()
                    )
                ) {
                    emit(NetWorkResponseState.Error(Throwable(errorMessage)))
                    return@flow
                }
                val loginPatient = LoginPatient(userName = userName, passWord = password.toString())
                repository.loginUser(loginPatient).collect { emit(it) }
            } else if (accountType == AccountManager.AccountType.DOCTOR.toString()) {
                if (email == null || !ValidationUtils.isEmailValid(email) || !ValidationUtils.isPasswordValid(
                        password.toString()
                    )
                ) {
                    emit(NetWorkResponseState.Error(Throwable(errorMessage)))
                    return@flow
                }
                val loginDoctor = LoginDoctor(email = email, passWord = password)
                repository.loginUser(loginDoctor).collect { emit(it) }
            }
        }
    }
}