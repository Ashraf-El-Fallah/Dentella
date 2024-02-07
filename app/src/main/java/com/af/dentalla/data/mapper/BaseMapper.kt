package com.af.dentalla.data.mapper

interface BaseMapper<I, O> {
    fun map(input: I): O
}