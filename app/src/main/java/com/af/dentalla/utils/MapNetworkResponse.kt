package com.af.dentalla.utils

import com.af.dentalla.data.NetWorkResponseState

fun <T : Any, R : Any> mapNetworkResponse(
    response: NetWorkResponseState<T>,
    mapper: (T) -> R
): NetWorkResponseState<R> {
    return when (response) {
        is NetWorkResponseState.Success -> NetWorkResponseState.Success(mapper(response.result))
        is NetWorkResponseState.Error -> NetWorkResponseState.Error(
            response.errorMessageResId,
            response.exception,
            response.statusCode
        )

        is NetWorkResponseState.Loading -> NetWorkResponseState.Loading
    }
}