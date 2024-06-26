package com.af.dentalla.domain.usecase.setting

import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.UserProfileInformation
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.domain.usecase.authentication.validations.ValidateEmailFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidatePhoneNumberFieldUseCase
import com.af.dentalla.domain.usecase.authentication.validations.ValidateUserNameFieldUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserProfileInformationUseCase @Inject constructor(
    private val validateUserNameFieldUseCase: ValidateUserNameFieldUseCase,
    private val validatePhoneNumberFieldUseCase: ValidatePhoneNumberFieldUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val repository: UserRepository
) {
    operator fun invoke(
        userProfileInformation: UserProfileInformation
    ): Flow<NetWorkResponseState<Unit>> {
        return flow {
            if (validateUserNameFieldUseCase(userProfileInformation.userName)) {
                emit(
                    NetWorkResponseState.Error(
                        errorMessageResId = R.string.user_name_not_valid,
                        exception = Throwable("Invalid data")
                    )
                )
                return@flow
            } else if (validatePhoneNumberFieldUseCase(userProfileInformation.phoneNumber)) {
                emit(
                    NetWorkResponseState.Error(
                        errorMessageResId = R.string.phone_not_valid,
                        exception = Throwable("Invalid data")
                    )

                )
                return@flow
            } else if (validateEmailFieldUseCase(userProfileInformation.email)) {
                emit(
                    NetWorkResponseState.Error(
                        errorMessageResId = R.string.email_not_valid,
                        exception = Throwable("Invalid data")
                    )

                )
                return@flow
            }
            repository.updateUserProfileInformation(userProfileInformation).collect { emit(it) }
        }
    }
}