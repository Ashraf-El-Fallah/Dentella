package com.af.dentalla.domain.usecase.patient

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.dto.CardsItemDto
import com.af.dentalla.domain.entity.AllCardsEntity
import com.af.dentalla.domain.mapper.ListMapper
import com.af.dentalla.domain.repository.PatientRepository
import com.af.dentalla.utilities.mapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllDoctorsCardsUseCase @Inject constructor(
    private val allCardsEntityMapper: ListMapper<CardsItemDto, AllCardsEntity>,
    private val repository: PatientRepository
) {
    operator fun invoke(): Flow<NetWorkResponseState<List<AllCardsEntity>>> {
        return repository.getAllDoctorsCards().map {
            it.mapResponse {
                allCardsEntityMapper.map(this)
            }
        }
    }
}