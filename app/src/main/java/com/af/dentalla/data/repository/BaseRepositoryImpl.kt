package com.af.dentalla.data.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.domain.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Exception

class BaseRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dataStorePreferencesService: DataStorePreferencesService
) : BaseRepository {
    override fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>> = flow {
        emit(NetWorkResponseState.Loading)
        try {
            val response = service.getAllArticles()
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(NetWorkResponseState.Success(data))
                } else {
                    emit(NetWorkResponseState.Error(Exception("Response body is null")))
                }
            } else {
                emit(NetWorkResponseState.Error(Exception("Http error ${response.body()}")))
            }
        } catch (e: Exception) {
            emit(NetWorkResponseState.Error(e))
        }
    }

    suspend fun saveToken(token: String?) {
        dataStorePreferencesService.saveTokenAndExpireDate(token)
    }
}