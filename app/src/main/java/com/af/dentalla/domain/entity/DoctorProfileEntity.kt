package com.af.dentalla.domain.entity

import com.af.dentalla.data.remote.dto.DoctorAvailability

data class DoctorProfileEntity(
    val about: String,
    val doctorAvailability: List<String>,
    val doctorName: String,
    val doctorPhoto: String,
    val phoneNumber: String,
    val specialty: Int
)
