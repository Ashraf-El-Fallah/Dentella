package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.CardsItemDto
import com.af.dentalla.domain.entity.AllCardsEntity
import com.af.dentalla.domain.mapper.ListMapper
import javax.inject.Inject

class AllCardsEntityMapper @Inject constructor() : ListMapper<CardsItemDto, AllCardsEntity> {
    override fun map(input: List<CardsItemDto>): List<AllCardsEntity> {
        return input.map {
            AllCardsEntity(
                cardId = it.cardId,
                currentUniversity = it.currentUniversity,
                doctorName = it.doctorName,
                doctorPhoto = it.doctorPhoto,
                phoneNumber = it.phoneNumber
            )
        }
    }

}