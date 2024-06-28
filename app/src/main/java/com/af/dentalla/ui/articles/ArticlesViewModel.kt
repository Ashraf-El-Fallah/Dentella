package com.af.dentalla.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.usecase.articles.AddArticleUseCase
import com.af.dentalla.domain.usecase.articles.GetArticlesUseCase
import com.af.dentalla.domain.usecase.articles.SaveArticleToDataBaseUseCase
import com.af.dentalla.utils.Event
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val addArticleUseCase: AddArticleUseCase,
    private val saveArticleToDataBaseUseCase: SaveArticleToDataBaseUseCase
) : ViewModel() {
    private val _articles = MutableLiveData<ScreenState<List<ArticlesEntity>>>()
    private val _addArticleState = MutableLiveData<Event<ScreenState<Unit>>>()

    val articles: LiveData<ScreenState<List<ArticlesEntity>>> get() = _articles
    val addArticleState: LiveData<Event<ScreenState<Unit>>> get() = _addArticleState

    private val _saveArticleToast = MutableLiveData<Event<Unit>>()
    val saveArticleToast: LiveData<Event<Unit>> get() = _saveArticleToast

    init {
        getArticles()
    }

    fun saveArticleToDataBase(articlesEntity: ArticlesEntity) {
        viewModelScope.launch {
            saveArticleToDataBaseUseCase(articlesEntity)
            _saveArticleToast.postValue(Event(Unit))
        }
    }

    private fun getArticles() {
        viewModelScope.launch {
            getArticlesUseCase().collectLatest {
                when (it) {
                    is NetWorkResponseState.Error -> _articles.postValue(
                        ScreenState.Error(
                            message = it.exception.message.toString()
                        )
                    )

                    is NetWorkResponseState.Loading -> _articles.postValue(ScreenState.Loading)
                    is NetWorkResponseState.Success -> _articles.postValue(
                        ScreenState.Success(
                            it.result
                        )
                    )
                }
            }
        }
    }

    fun addArticle(article: Article) {
        viewModelScope.launch {
            addArticleUseCase(article).collect {
                when (it) {
                    is NetWorkResponseState.Error -> _addArticleState.postValue(
                        Event(
                            ScreenState.Error(
                                message = it.exception.message.toString()
                            )
                        )
                    )

                    is NetWorkResponseState.Loading -> _addArticleState.postValue(Event(ScreenState.Loading))
                    is NetWorkResponseState.Success -> _addArticleState.postValue(
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