package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.mapper.ListMapper
import javax.inject.Inject

class ArticlesEntityMapper @Inject constructor() : ListMapper<ArticleDto, ArticlesEntity> {
    override fun map(input: List<ArticleDto>): List<ArticlesEntity> {
        return input.map {
            ArticlesEntity(
                articleId = it.articleId,
                content = it.content,
                postingTime = it.createdAt,
                doctorName = it.doctorName,
                doctorImage = it.imageUrl,
                numberOfComments = it.numberOfComments,
                title = it.title
            )
        }
    }

}