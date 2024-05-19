package com.af.dentalla.domain.usecase.authentication

import com.af.dentalla.data.local.DataStorePreferencesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val dataStorePreferencesService: DataStorePreferencesService) {
    suspend operator fun invoke(): Flow<String?> {
        return flow {
            emit(dataStorePreferencesService.getToken())
        }
    }

}