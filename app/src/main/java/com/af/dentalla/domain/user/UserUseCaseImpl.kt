package com.af.dentalla.domain.user

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.User
import com.af.dentalla.domain.entity.UserResponseEntity
import com.af.dentalla.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val repository: DoctorRepository
) : UserUseCase {
    override fun invoke(user: User): Flow<NetWorkResponseState<UserResponseEntity>> {
        return repository.login(user)
    }

}