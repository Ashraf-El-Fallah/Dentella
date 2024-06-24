package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.remote.requests.UserPasswords
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class ChangeUserPasswordUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(
        userPasswords: UserPasswords
    ) = repository.changeUserPassword(userPasswords)
}