package com.af.dentalla.domain.usecase.articles

import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class DeleteArticleFromDataBaseUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        articleSavedEntity: ArticleSavedEntity
    ) {
        repository.deleteSavedArticle(articleSavedEntity)
    }
}