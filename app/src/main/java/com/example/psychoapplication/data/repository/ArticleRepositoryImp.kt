package com.example.psychoapplication.data.repository

import com.example.psychoapplication.data.model.Article
import com.example.psychoapplication.util.FireStoreCollection
import com.example.psychoapplication.util.FireStoreDocumentField
import com.example.psychoapplication.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ArticleRepositoryImp (
    val auth: FirebaseAuth,
    private val database: FirebaseFirestore,
): ArticleRepository{

    override fun getArticles(result: (UiState<List<Article>>) -> Unit) {
        database.collection(FireStoreCollection.ARTICLES)
            .orderBy(FireStoreDocumentField.DATE, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val articles = arrayListOf<Article>()
                for (document in it) {
                    val article = document.toObject(Article::class.java)
                    articles.add(article)
                }
                result.invoke(
                    UiState.Success(articles)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}