package com.af.dentalla.ui.patient.doctorspecialities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.usecase.patient.GetSpecialityCardsUseCase
import com.af.dentalla.utils.Event
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsSpecialitiesViewModel @Inject constructor(
    private val getSpecialityCardsUseCase: GetSpecialityCardsUseCase
) : ViewModel() {

    private val _specialityCards = MutableLiveData<Event<ScreenState<List<CardsEntity>>>>()
    val specialityCards: LiveData<Event<ScreenState<List<CardsEntity>>>> get() = _specialityCards
    fun getDoctorsSpecialityCards(specialityId: Int) {
        viewModelScope.launch {
            getSpecialityCardsUseCase(specialityId).collectLatest {
                when (it) {
                    is NetWorkResponseState.Error -> _specialityCards.postValue(
                        Event(
                            ScreenState.Error()
                        )
                    )

                    is NetWorkResponseState.Loading -> _specialityCards.postValue(Event(ScreenState.Loading))
                    is NetWorkResponseState.Success -> _specialityCards.postValue(
                        Event(
                            ScreenState.Success(
                                it.result
                            )
                        )
                    )
                }
            }
        }
    }
}