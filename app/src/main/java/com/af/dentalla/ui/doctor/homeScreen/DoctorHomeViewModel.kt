package com.af.dentalla.ui.doctor.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.domain.usecase.doctor.GetAllPostsUseCase
import com.af.dentalla.utilities.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorHomeViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase
) : ViewModel() {
    private val _allPosts = MutableLiveData<ScreenState<List<PostEntity>>>()
    val allPosts: LiveData<ScreenState<List<PostEntity>>> get() = _allPosts

    init {
        getAllPosts()
    }

    private fun getAllPosts() {
        viewModelScope.launch {
            getAllPostsUseCase().collectLatest {
                when (it) {
                    is NetWorkResponseState.Loading -> _allPosts.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _allPosts.postValue(ScreenState.Success(it.result))
                    is NetWorkResponseState.Error -> _allPosts.postValue(ScreenState.Error(it.exception.message.toString()))

                }
            }
        }
    }
}