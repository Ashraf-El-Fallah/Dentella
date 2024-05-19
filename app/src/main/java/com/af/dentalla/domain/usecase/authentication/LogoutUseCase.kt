package com.af.dentalla.domain.usecase.authentication

import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.logout()
}