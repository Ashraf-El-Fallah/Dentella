package com.af.dentalla.domain.usecase.patient.signup

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.domain.entity.SignUpEntity
import kotlinx.coroutines.flow.Flow

interface SignUpPatientUseCase {
    suspend operator fun invoke(signUpPatient: SignUpPatient): Flow<NetWorkResponseState<SignUpEntity>>
}