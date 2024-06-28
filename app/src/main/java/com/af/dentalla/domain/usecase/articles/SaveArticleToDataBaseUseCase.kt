package com.af.dentalla.domain.usecase.articles

import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class SaveArticleToDataBaseUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        articlesEntity: ArticlesEntity
    ) {
        repository.insertArticleToDataBase(articlesEntity)
    }
}