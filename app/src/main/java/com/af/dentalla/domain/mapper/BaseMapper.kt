package com.af.dentalla.domain.mapper

interface BaseMapper<I, O> {
    fun map(input: I): O
}