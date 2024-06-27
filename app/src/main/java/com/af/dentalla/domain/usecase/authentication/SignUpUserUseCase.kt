package com.af.dentalla.domain.usecase.authentication

import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.domain.usecase.authentication.validations.*
import com.af.dentalla.utils.AccountManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePhoneNumberFieldUseCase: ValidatePhoneNumberFieldUseCase,
    private val validatePasswordAndConfirmationUseCase: ValidatePasswordAndConfirmationUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val validateIdUseCase: ValidateIdUseCase
) {
    fun execute(
        accountType: String,
        userName: String?,
        email: String?,
        phone: String?,
        password: String?,
        confirmPassword: String?,
        id: String? = null
    ): Flow<NetWorkResponseState<Unit>> {
        return flow {
            val error = validateInput(
                accountType,
                userName,
                email,
                phone,
                password,
                confirmPassword,
                id
            )
            if (error != null) {
                emit(error)
                return@flow
            }

            when (accountType) {
                AccountManager.AccountType.PATIENT.toString() -> {
                    val signUpPatient = SignUpPatient(
                        username = userName.toString(),
                        email = email.toString(),
                        password = password.toString(),
                        phoneNumber = phone.toString()
                    )
                    repository.signUpUser(signUpPatient).collect { emit(it) }
                }

                AccountManager.AccountType.DOCTOR.toString() -> {
                    val signUpDoctor = SignUpDoctor(
                        username = userName.toString(),
                        id = id.toString(),
                        email = email.toString(),
                        password = password.toString(),
                        phoneNumber = phone.toString()
                    )
                    repository.signUpUser(signUpDoctor).collect { emit(it) }
                }

                else -> {
                    emit(
                        NetWorkResponseState.Error(
                            R.string.invalid_data,
                            Throwable("Invalid data")
                        )
                    )
                }
            }
        }
    }

    private fun validateInput(
        accountType: String,
        userName: String?,
        email: String?,
        phone: String?,
        password: String?,
        confirmPassword: String?,
        id: String?
    ): NetWorkResponseState.Error? {
        return when {
            validateUserNameFieldUseCase(userName.toString()) -> {
                NetWorkResponseState.Error(
                    errorMessageResId = R.string.user_name_not_valid,
                    Throwable("Invalid data")
                )
            }

            accountType == AccountManager.AccountType.DOCTOR.toString() && validateIdUseCase(id.toString()) -> {
                NetWorkResponseState.Error(
                    errorMessageResId = R.string.id_not_valid,
                    Throwable("Invalid data")
                )
            }

            validateEmailFieldUseCase(email.toString()) -> {
                NetWorkResponseState.Error(
                    errorMessageResId = R.string.email_not_valid,
                    Throwable("Invalid data")
                )
            }

            validatePhoneNumberFieldUseCase(phone.toString()) -> {
                NetWorkResponseState.Error(
                    errorMessageResId = R.string.phone_not_valid,
                    Throwable("Invalid data")
                )
            }

            validatePasswordAndConfirmationUseCase(
                password.toString(),
                confirmPassword.toString()
            ) -> {
                NetWorkResponseState.Error(
                    errorMessageResId = R.string.confirmation_password_not_valid,
                    Throwable("Invalid data")
                )
            }

            else -> null
        }
    }
}
