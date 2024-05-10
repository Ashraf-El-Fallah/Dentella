package com.af.dentalla.ui.patient.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.usecase.patient.GetCardsBySearchByUniversityUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCardsBySearchByUniversityUseCase: GetCardsBySearchByUniversityUseCase
) : ViewModel() {
    private val _searchedCards = MutableLiveData<ScreenState<List<CardsEntity>>>()
    val searchedCards: LiveData<ScreenState<List<CardsEntity>>> get() = _searchedCards
    fun getCardsBySearch(university: String) {
        viewModelScope.launch {
            getCardsBySearchByUniversityUseCase(university).collectLatest {
                when (it) {
                    is NetWorkResponseState.Error -> _searchedCards.postValue(ScreenState.Error(it.exception.message!!))
                    is NetWorkResponseState.Success -> _searchedCards.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )

                    is NetWorkResponseState.Loading -> _searchedCards.postValue(ScreenState.Loading)
                }
            }
        }
    }
}