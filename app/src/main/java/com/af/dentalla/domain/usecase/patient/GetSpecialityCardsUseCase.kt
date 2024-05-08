package com.af.dentalla.domain.usecase.patient

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.mapper.ListMapper
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.mapResponse
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecialityCardsUseCase @Inject constructor(
    private val cardsEntityMapper: ListMapper<CardsDto, CardsEntity>,
    private val repository: UserRepository
) {
    operator fun invoke(specialityId: Int): Flow<NetWorkResponseState<List<CardsEntity>>> {
        return repository.getSpecialityDoctorsCards(specialityId).map {
            it.mapResponse { cardsEntityMapper.map(this) }
        }
    }
}