package com.af.dentalla.domain.usecase.articles

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSavedArticlesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<NetWorkResponseState<List<ArticleSavedEntity>>> =
        repository.getSavedArticlesFromDataBase()
}