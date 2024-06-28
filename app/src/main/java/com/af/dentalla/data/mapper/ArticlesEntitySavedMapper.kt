package com.af.dentalla.data.mapper

import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.mapper.BaseMapper
import javax.inject.Inject

class ArticlesEntitySavedMapper @Inject constructor() :
    BaseMapper<ArticlesEntity, ArticleSavedEntity> {
//    private var userType: String = ""
//
//    constructor(userType: String) : this() {
//        this.userType = userType
//    }

    override fun map(input: ArticlesEntity): ArticleSavedEntity {
        return ArticleSavedEntity(
            articleId = input.articleId,
            content = input.content,
            postingTime = input.postingTime,
            doctorName = input.doctorName,
            doctorImage = input.doctorImage ?: "",
            articleImage = input.articleImage ?: "",
            title = input.title
        )
    }


}