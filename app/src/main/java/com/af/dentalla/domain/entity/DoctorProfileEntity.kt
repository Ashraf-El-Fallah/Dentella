package com.af.dentalla.domain.entity


data class DoctorProfileEntity(
    val about: String,
    val doctorAvailability: List<DoctorAvailabilityEntity>,
    val doctorName: String,
    val doctorPhoto: String,
    val phoneNumber: String,
    val specialty: Int
)