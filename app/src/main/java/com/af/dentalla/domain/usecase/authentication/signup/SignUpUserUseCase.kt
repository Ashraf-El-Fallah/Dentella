package com.af.dentalla.domain.usecase.authentication.signup

import android.app.Application
import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.GetStringUtil.getString
import com.af.dentalla.utils.ValidationUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val application: Application
) {
//    private fun validateUserName(userName: String?) =
//        userName == null || !ValidationUtils.isUserNameValid(userName)

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
            when (accountType) {
                AccountManager.AccountType.PATIENT.toString() -> {
                    when {
                        userName == null || !ValidationUtils.isUserNameValid(userName) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.user_name_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        !ValidationUtils.isEmailValid(email.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.email_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        !ValidationUtils.isPhoneNumberValid(phone.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.phone_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        !ValidationUtils.isPasswordAndConfirmationEqual(
                            password.toString(),
                            confirmPassword.toString()
                        ) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.confirmation_password_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        else -> {
                            val signUpPatient = SignUpPatient(
                                username = userName,
                                email = email.toString(),
                                password = password.toString(),
                                phoneNumber = phone.toString()
                            )
                            repository.signUpUser(signUpPatient).collect { emit(it) }
                        }
                    }
                }

                AccountManager.AccountType.DOCTOR.toString() -> {
                    when {
                        id == null || !ValidationUtils.isIdValid(id) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.id_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        userName == null || !ValidationUtils.isUserNameValid(userName.toString()) -> {
                            NetWorkResponseState.Error(
                                Throwable(
                                    getString(
                                        application,
                                        R.string.user_name_not_valid
                                    )
                                )
                            )
                            return@flow
                        }

                        !ValidationUtils.isEmailValid(email.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.email_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        !ValidationUtils.isPhoneNumberValid(phone.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.phone_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        !ValidationUtils.isPasswordAndConfirmationEqual(
                            password.toString(),
                            confirmPassword.toString()
                        ) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.confirmation_password_not_valid
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        else -> {
                            val signUpDoctor = SignUpDoctor(
                                username = userName.toString(),
                                id = id,
                                email = email.toString(),
                                password = password.toString(),
                                phoneNumber = phone.toString()
                            )
                            repository.signUpUser(signUpDoctor).collect { emit(it) }
                        }
                    }
                }

                else -> {
                    emit(
                        NetWorkResponseState.Error(
                            Throwable(
                                getString(
                                    application,
                                    R.string.invalid_data
                                )
                            )
                        )
                    )
                }
            }
        }
    }
}