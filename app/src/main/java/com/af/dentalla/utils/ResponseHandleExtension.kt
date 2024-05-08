package com.af.dentalla.utils

import com.af.dentalla.data.NetWorkResponseState

fun <I : Any, O : Any> NetWorkResponseState<I>.mapResponse(mapper: I.() -> O): NetWorkResponseState<O> {
    return when (this) {
        is NetWorkResponseState.Error -> NetWorkResponseState.Error(this.exception)
        NetWorkResponseState.Loading -> NetWorkResponseState.Loading
        is NetWorkResponseState.Success -> NetWorkResponseState.Success(mapper.invoke(this.result))
    }
}