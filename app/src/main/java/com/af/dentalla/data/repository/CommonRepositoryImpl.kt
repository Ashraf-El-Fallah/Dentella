package com.af.dentalla.data.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.ArticleDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Exception

//class CommonRepositoryImpl @Inject constructor(
//    private val service: ApiService,
//    private val dataStorePreferencesService: DataStorePreferencesService
//) : BaseRepository {
//
//}