package com.example.psychoapplication.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.psychoapplication.data.model.Article
import com.example.psychoapplication.data.repository.ArticleRepository
import com.example.psychoapplication.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    val repository: ArticleRepository
): ViewModel() {

    private val _articles = MutableLiveData<UiState<List<Article>>>()
    val articles: LiveData<UiState<List<Article>>>
        get() = _articles

    fun getArticles() {
        _articles.value = UiState.Loading
        repository.getArticles { _articles.value = it }
    }
}