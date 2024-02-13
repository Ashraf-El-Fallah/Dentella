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
                articleImage = it.imageUrl,
                numberOfComments = it.numberOfComments,
                title = it.title
            )
        }
    }

}

fun ArticleDto.toDomainModel(): ArticlesEntity =
    ArticlesEntity(
        articleId = articleId,
        content = content,
        postingTime = createdAt,
        doctorName = doctorName,
        articleImage = imageUrl,
        numberOfComments = numberOfComments,
        title = title
    )