package com.af.dentalla.domain.usecase.setting

import android.app.Application
import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.UserProfileInformation
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.domain.usecase.authentication.validations.ValidateEmailFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidatePhoneNumberFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidateUserNameFieldUseCase
import com.af.dentalla.utils.GetStringUtil.getString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class UpdateUserProfileInformationUseCase @Inject constructor(
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePhoneNumberFieldUseCase: ValidatePhoneNumberFieldUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val repository: UserRepository,
    private val application: Application
) {
    operator fun invoke(
        userProfileInformation: UserProfileInformation
    ): Flow<NetWorkResponseState<Unit>> {
        return flow {
            val language = Locale.getDefault().language
            if (validateUserNameFieldUseCase(userProfileInformation.userName)) {
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
            } else if (validatePhoneNumberFieldUseCase(userProfileInformation.phoneNumber)) {
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
            } else if (validateEmailFieldUseCase(userProfileInformation.email)) {
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
            repository.updateUserProfileInformation(userProfileInformation).collect { emit(it) }
        }
    }
}