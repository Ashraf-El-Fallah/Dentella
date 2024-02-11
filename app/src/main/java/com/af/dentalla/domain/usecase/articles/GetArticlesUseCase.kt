package com.af.dentalla.domain.usecase.articles

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.mapper.ListMapper
import com.af.dentalla.domain.repository.BaseRepository
import com.af.dentalla.utilities.mapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val allArticlesEntityMapper: ListMapper<ArticleDto, ArticlesEntity>,
    private val repository: BaseRepository
) {
    operator fun invoke(): Flow<NetWorkResponseState<List<ArticlesEntity>>> {
        return repository.getAllArticles().map {
            it.mapResponse {
                allArticlesEntityMapper.map(this)
            }
        }
    }
}