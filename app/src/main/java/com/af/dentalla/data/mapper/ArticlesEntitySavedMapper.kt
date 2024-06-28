package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.mapper.BaseMapper
import com.af.dentalla.domain.mapper.ListMapper
import javax.inject.Inject

class ArticlesEntitySavedMapper @Inject constructor() :
    BaseMapper<ArticlesEntity, ArticleSavedEntity> {
    override fun map(input: ArticlesEntity): ArticleSavedEntity {
        return ArticleSavedEntity(
            articleId = input.articleId ?: 0,
            content = input.content ?: "",
            postingTime = input.postingTime ?: "",
            doctorName = input.doctorName ?: "",
            doctorImage = input.doctorImage ?: "",
            articleImage = input.articleImage ?: "",
            title = input.title ?: ""
        )
    }
}