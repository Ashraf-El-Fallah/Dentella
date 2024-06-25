package com.af.dentalla.domain.usecase.patient

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsBySearchByUniversityUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(university: String): Flow<NetWorkResponseState<List<CardsEntity>>> =
        repository.getCardsBySearchByUniversity(university)
}