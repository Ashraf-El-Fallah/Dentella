package com.af.dentalla.domain.usecase.doctor.signup

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.SignUpDoctor
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.domain.entity.SignUpEntity
import kotlinx.coroutines.flow.Flow

interface SignUpDoctorUseCase {
    suspend operator fun invoke(signUpDoctor: SignUpDoctor): Flow<NetWorkResponseState<SignUpEntity>>
}