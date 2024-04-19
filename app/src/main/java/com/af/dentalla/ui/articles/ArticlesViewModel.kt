package com.af.dentalla.ui.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.usecase.articles.AddArticleUseCase
import com.af.dentalla.domain.usecase.articles.GetArticlesUseCase
import com.af.dentalla.utilities.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val addArticleUseCase: AddArticleUseCase
) : ViewModel() {
    private val _articles = MutableLiveData<ScreenState<List<ArticlesEntity>>>()
    val articles: LiveData<ScreenState<List<ArticlesEntity>>> get() = _articles

    init {
        getArticles()
    }

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

    fun addArticle(article: Article) {
        viewModelScope.launch {
            Log.d("ARTICLE","...........")
            addArticleUseCase(article)
        }
    }
}