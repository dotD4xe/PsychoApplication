package com.example.psychoapplication.data.repository

import com.example.psychoapplication.data.model.Article
import com.example.psychoapplication.util.UiState

interface ArticleRepository {
    fun getArticles(result: (UiState<List<Article>>) -> Unit)
}