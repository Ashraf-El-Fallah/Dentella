package com.af.dentalla.ui.doctor.addCard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.domain.usecase.doctor.AddCardUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(private val addCardUseCase: AddCardUseCase) :
    ViewModel() {
    private val _addCardState = MutableLiveData<ScreenState<Unit>>()
    val addCardState: LiveData<ScreenState<Unit>> get() = _addCardState

    fun addCard(card: Card) {
        viewModelScope.launch {
            addCardUseCase(card).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _addCardState.postValue(
                        ScreenState.Error(
                            statusCode = it.statusCode
                        )
                    )

                    is NetWorkResponseState.Loading -> _addCardState.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _addCardState.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )
                }
            }
        }
    }
}