package com.af.dentalla.domain.usecase.authentication

import android.app.Application
import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.domain.usecase.authentication.validations.ValidateEmailFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidateIdUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidatePasswordAndConfirmationUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidatePhoneNumberFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidateUserNameFieldUseCase
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.GetStringUtil.getString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val application: Application,
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
            val language = Locale.getDefault().language

            when (accountType) {
                AccountManager.AccountType.PATIENT.toString() -> {
                    when {
                        validateUserNameFieldUseCase(userName.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.user_name_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        validateEmailFieldUseCase(email.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.email_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        validatePhoneNumberFieldUseCase(phone.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.phone_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        validatePasswordAndConfirmationUseCase(
                            password.toString(),
                            confirmPassword.toString()
                        ) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.confirmation_password_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        else -> {
                            val signUpPatient = SignUpPatient(
                                username = userName.toString(),
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
                        validateUserNameFieldUseCase(userName.toString()) -> {
                            NetWorkResponseState.Error(
                                Throwable(
                                    getString(
                                        application,
                                        R.string.user_name_not_valid,
                                        language
                                    )
                                )
                            )
                            return@flow
                        }

                        validateIdUseCase(id.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.id_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        validateEmailFieldUseCase(email.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.email_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        validatePhoneNumberFieldUseCase(phone.toString()) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.phone_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        validatePasswordAndConfirmationUseCase(
                            password.toString(),
                            confirmPassword.toString()
                        ) -> {
                            emit(
                                NetWorkResponseState.Error(
                                    Throwable(
                                        getString(
                                            application,
                                            R.string.confirmation_password_not_valid,
                                            language
                                        )
                                    )
                                )
                            )
                            return@flow
                        }

                        else -> {
                            val signUpDoctor = SignUpDoctor(
                                username = userName.toString(),
                                id = id.toString(),
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
                                    R.string.invalid_data,
                                    language
                                )
                            )
                        )
                    )
                }
            }
        }
    }
}