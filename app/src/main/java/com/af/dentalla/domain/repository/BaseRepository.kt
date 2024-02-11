package com.af.dentalla.domain.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>>

}