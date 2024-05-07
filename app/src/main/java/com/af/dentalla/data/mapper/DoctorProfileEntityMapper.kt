package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.DoctorAvailability
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.domain.entity.DoctorAvailabilityEntity
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.mapper.BaseMapper
import javax.inject.Inject

class DoctorProfileEntityMapper @Inject constructor() :
    BaseMapper<DoctorProfileDto, DoctorProfileEntity> {
    override fun map(input: DoctorProfileDto): DoctorProfileEntity {
        return DoctorProfileEntity(
            about = input.bio ?: "",
            doctorName = input.doctorName ?: "",
            doctorPhoto = input.doctorPhoto ?: "",
            phoneNumber = input.phoneNumber ?: "",
            specialty = input.specialty ?: 0,
            doctorAvailability = mapAvailabilityDtoToEntity(input.doctorAvailability ?: emptyList())
        )
    }

    private fun mapAvailabilityDtoToEntity(availabilityDtoList: List<DoctorAvailability>): List<DoctorAvailabilityEntity> {
        return availabilityDtoList.map { doctorAvailability ->
            DoctorAvailabilityEntity(doctorAvailability.availableDates)
        }
    }
}