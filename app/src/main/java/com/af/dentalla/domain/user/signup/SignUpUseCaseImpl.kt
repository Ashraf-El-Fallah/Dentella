package com.af.dentalla.domain.user.signup

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.SignUpUser
import com.af.dentalla.domain.entity.SignUpResponseEntity
import com.af.dentalla.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val repository: DoctorRepository
) : SignUpUseCase {
    override fun invoke(signUpUser: SignUpUser): Flow<NetWorkResponseState<SignUpResponseEntity>> {
        return repository.signUp(signUpUser)
    }

}