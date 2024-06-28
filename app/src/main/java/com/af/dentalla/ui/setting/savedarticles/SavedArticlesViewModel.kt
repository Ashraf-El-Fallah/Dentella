package com.af.dentalla.ui.setting.savedarticles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.domain.usecase.articles.DeleteArticleFromDataBaseUseCase
import com.af.dentalla.domain.usecase.articles.GetAllSavedArticlesUseCase
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedArticlesViewModel @Inject constructor(
    private val getAllSavedArticlesUseCase: GetAllSavedArticlesUseCase,
    private val deleteArticleFromDataBaseUseCase: DeleteArticleFromDataBaseUseCase
): ViewModel() {

    private val _savedArticles = MutableLiveData<ScreenState<List<ArticleSavedEntity>>>()
    val savedArticles: LiveData<ScreenState<List<ArticleSavedEntity>>> get() = _savedArticles

    init {
        getAllSavedArticles()
    }

    private fun getAllSavedArticles() {
        viewModelScope.launch {
            getAllSavedArticlesUseCase().collect {
                when (it) {
                    is NetWorkResponseState.Error -> _savedArticles.postValue(
                        ScreenState.Error(
                            message = it.exception.message.toString()
                        )
                    )

                    is NetWorkResponseState.Loading -> _savedArticles.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _savedArticles.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )
                }
            }
        }
    }
}