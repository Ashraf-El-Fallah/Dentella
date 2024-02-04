package com.af.dentalla.domain.user.login

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.domain.entity.LoginResponseEntity
import com.af.dentalla.domain.repository.DoctorRepository
import com.af.dentalla.domain.user.login.LoginUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository: DoctorRepository
) : LoginUseCase {
    override fun invoke(loginUser: LoginUser): Flow<NetWorkResponseState<LoginResponseEntity>> {
        return repository.login(loginUser)
    }

}