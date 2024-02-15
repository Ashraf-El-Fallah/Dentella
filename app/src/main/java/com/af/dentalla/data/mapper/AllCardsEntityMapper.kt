package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.mapper.ListMapper
import javax.inject.Inject

class AllCardsEntityMapper @Inject constructor() : ListMapper<CardsDto, CardsEntity> {
    override fun map(input: List<CardsDto>): List<CardsEntity> {
        return input.map {
            CardsEntity(
                cardId = it.cardId,
                currentUniversity = it.currentUniversity,
                doctorName = it.doctorName,
                doctorPhoto = it.doctorPhoto,
                phoneNumber = it.phoneNumber
            )
        }
    }

    fun CardsDto.toCardsEntity(): CardsEntity =
        CardsEntity(
            cardId = cardId,
            currentUniversity = currentUniversity,
            doctorName = doctorName,
            doctorPhoto = doctorPhoto,
            phoneNumber = phoneNumber
        )

}