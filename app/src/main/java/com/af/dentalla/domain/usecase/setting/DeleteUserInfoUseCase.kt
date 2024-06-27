package com.af.dentalla.domain.usecase.setting

import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserInfoUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.deleteUserInfo()
}