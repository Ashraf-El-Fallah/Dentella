package com.af.dentalla.domain.usecase.setting

import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.UserPasswords
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.domain.usecase.authentication.validations.ValidatePasswordFieldUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: UserRepository,
    private val validatePasswordFieldUseCase: ValidatePasswordFieldUseCase
) {
    fun execute(
        oldPassword: String,
        newPassword: String
    ): Flow<NetWorkResponseState<Unit>> {
        return flow {
            if (validatePasswordFieldUseCase(newPassword) || newPassword == oldPassword || validatePasswordFieldUseCase(
                    oldPassword
                )
            ) {
                emit(
                    NetWorkResponseState.Error(
                        errorMessageResId = R.string.error_when_changing_password,
                        exception = Throwable("Invalid data")
                    )

                )
                return@flow
            }
            val userPasswords = UserPasswords(oldPassword = oldPassword, newPassword = newPassword)
            repository.changeUserPassword(userPasswords).collect { emit(it) }
        }
    }
}