package com.af.dentalla.data.mapper

import com.af.dentalla.data.remote.dto.DoctorAvailability
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.mapper.BaseMapper
import com.af.dentalla.domain.mapper.ListMapper
import javax.inject.Inject

class DoctorProfileEntityMapper @Inject constructor() :
    BaseMapper<DoctorProfileDto, DoctorProfileEntity> {
//    override fun map(input: List<DoctorProfileDto>): List<DoctorProfileEntity> {
//
//    }

    override fun map(input: DoctorProfileDto): DoctorProfileEntity {
        return DoctorProfileEntity(
            about = input.bio,
            doctorAvailability = input.doctorAvailability,
            doctorName = input.doctorName,
            doctorPhoto = input.doctorPhoto,
            phoneNumber = input.phoneNumber,
            specialty = input.specialty
        )
    }
}