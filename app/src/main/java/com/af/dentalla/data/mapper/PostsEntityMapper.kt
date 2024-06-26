package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.domain.mapper.ListMapper
import javax.inject.Inject

class PostsEntityMapper @Inject constructor() : ListMapper<PostDtoItem, PostEntity> {
    override fun map(input: List<PostDtoItem>): List<PostEntity> {
        return input.map {
            PostEntity(
                content = it.content ?: "",
                patientName = it.patientName ?: "",
                patientPhoto = it.patientPhoto,
                postId = it.postId ?: 0,
                phoneNumber = it.phoneNumber,
            )
        }
    }

}