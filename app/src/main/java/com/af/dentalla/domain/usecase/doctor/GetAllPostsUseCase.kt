package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.domain.mapper.ListMapper
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.mapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val allPostsEntityMapper: ListMapper<PostDtoItem, PostEntity>,
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<NetWorkResponseState<List<PostEntity>>> {
        return repository.getAllPosts().map {
            it.mapResponse {
                allPostsEntityMapper.map(this)
            }
        }
    }
}