package com.af.dentalla.domain.usecase.doctor

import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.domain.repository.UserRepository
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(
        card: Card
    ) = repository.addCard(card)
}