package com.af.dentalla.ui.patient.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.usecase.articles.GetArticlesUseCase
import com.af.dentalla.utilities.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {
    private val _articles = MutableLiveData<ScreenState<List<ArticlesEntity>>>()
    val articles: LiveData<ScreenState<List<ArticlesEntity>>> get() = _articles

    private fun getArticles() {
        viewModelScope.launch {
            getArticlesUseCase().collectLatest {
                when (it) {
                    is NetWorkResponseState.Error -> _articles.postValue(ScreenState.Error(it.exception.message!!))
                    is NetWorkResponseState.Loading -> _articles.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _articles.postValue(ScreenState.Success(it.result))
                }
            }
        }
    }
}