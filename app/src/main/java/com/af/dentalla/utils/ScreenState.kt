package com.af.dentalla.utils

sealed class ScreenState<out T : Any> {
    object Loading : ScreenState<Nothing>()

    //    data class Error(val message: String) : ScreenState<Nothing>()
    data class Error(val message: String?, val statusCode: Int? = null) : ScreenState<Nothing>()

    data class Success<out T : Any>(val uiData: T) : ScreenState<T>()
}