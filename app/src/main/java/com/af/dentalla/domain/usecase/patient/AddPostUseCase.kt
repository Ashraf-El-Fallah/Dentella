package com.af.dentalla.domain.usecase.patient

import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class AddPostUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(post: Post) = repository.addPost(post)
}