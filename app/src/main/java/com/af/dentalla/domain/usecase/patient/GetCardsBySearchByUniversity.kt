package com.af.dentalla.domain.usecase.patient

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.mapper.ListMapper
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utilities.mapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCardsBySearchByUniversity @Inject constructor(
    private val cardsEntity: ListMapper<CardsDto, CardsEntity>,
    private val repository: UserRepository
) {
    operator fun invoke(university: String): Flow<NetWorkResponseState<List<CardsEntity>>> {
        return repository.getCardsBySearchByUniversity(university).map {
            it.mapResponse { cardsEntity.map(this) }
        }
    }
}