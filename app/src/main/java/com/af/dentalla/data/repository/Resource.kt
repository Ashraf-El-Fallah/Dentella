package com.af.dentalla.data.repository

/**
 * @author Mohannad El-Sayeh email(eng.mohannadelsayeh@gmail.com)
 * @date 14/02/2024
 */

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val error: Throwable? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(error: Throwable?, data: T? = null) : Resource<T>(data, error)
}