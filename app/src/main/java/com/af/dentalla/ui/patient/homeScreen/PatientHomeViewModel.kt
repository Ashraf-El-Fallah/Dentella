package com.af.dentalla.ui.patient.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.AllCardsEntity
import com.af.dentalla.domain.usecase.patient.GetAllDoctorsCardsUseCase
import com.af.dentalla.utilities.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientHomeViewModel @Inject constructor(
    private val getAllDoctorsCardsUseCase: GetAllDoctorsCardsUseCase
) : ViewModel() {
    private val _allCards = MutableLiveData<ScreenState<List<AllCardsEntity>>>()
    val allCards: LiveData<ScreenState<List<AllCardsEntity>>> get() = _allCards

    init {
        getAllCards()
    }

    private fun getAllCards() {
        viewModelScope.launch {
            getAllDoctorsCardsUseCase().collectLatest {
                when (it) {
                    is NetWorkResponseState.Error -> _allCards.postValue(ScreenState.Error(it.exception.message!!))
                    is NetWorkResponseState.Success -> _allCards.postValue(ScreenState.Success(it.result))
                    is NetWorkResponseState.Loading -> _allCards.postValue(ScreenState.Loading)
                }
            }
        }
    }

}