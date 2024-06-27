package com.af.dentalla.ui.patient.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.usecase.patient.AddPostUseCase
import com.af.dentalla.domain.usecase.patient.GetAllDoctorsCardsUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientHomeViewModel @Inject constructor(
    private val getAllDoctorsCardsUseCase: GetAllDoctorsCardsUseCase,
    private val addPostUseCase: AddPostUseCase
) : ViewModel() {
    private val _allCards = MutableLiveData<ScreenState<List<CardsEntity>>>()
    private val _addPostState = MutableLiveData<ScreenState<Unit>>()
    val allCards: LiveData<ScreenState<List<CardsEntity>>> get() = _allCards
    val addPostState: LiveData<ScreenState<Unit>> get() = _addPostState

    init {
        getAllCards()
    }

    private fun getAllCards() {
        viewModelScope.launch {
            getAllDoctorsCardsUseCase().collectLatest {
                when (it) {
                    is NetWorkResponseState.Error -> _allCards.postValue(ScreenState.Error(message = it.exception.message.toString()))
                    is NetWorkResponseState.Success -> _allCards.postValue(ScreenState.Success(it.result))
                    is NetWorkResponseState.Loading -> _allCards.postValue(ScreenState.Loading)
                }
            }
        }
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            addPostUseCase(post).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _addPostState.postValue(
                        ScreenState.Error(
                            message = it.exception.message.toString()
                        )
                    )

                    is NetWorkResponseState.Success -> _addPostState.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )

                    is NetWorkResponseState.Loading -> _addPostState.postValue(ScreenState.Loading)
                }
            }
        }
    }


}