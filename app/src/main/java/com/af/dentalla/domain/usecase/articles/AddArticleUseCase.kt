package com.af.dentalla.domain.usecase.articles

import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class AddArticleUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(
        article: Article
    ) = repository.addArticle(article)
}