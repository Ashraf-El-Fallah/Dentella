package com.af.dentalla.ui.patient.doctorsSpecialities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.usecase.patient.GetSpecialityCardsUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsSpecialitiesViewModel @Inject constructor(
    private val getSpecialityCardsUseCase: GetSpecialityCardsUseCase
) : ViewModel() {

    private val _specialityCards = MutableLiveData<ScreenState<List<CardsEntity>>>()
    val specialityCards: LiveData<ScreenState<List<CardsEntity>>> get() = _specialityCards
    fun getDoctorsSpecialityCards(specialityId: Int) {
        viewModelScope.launch {
            getSpecialityCardsUseCase(specialityId).collectLatest {
                when (it) {
                    is NetWorkResponseState.Error -> _specialityCards.postValue(ScreenState.Error(message = it.exception.message.toString()))
                    is NetWorkResponseState.Loading -> _specialityCards.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _specialityCards.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )
                }
            }
        }
    }
}