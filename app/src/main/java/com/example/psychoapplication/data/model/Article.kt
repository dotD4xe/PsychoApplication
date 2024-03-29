package com.example.psychoapplication.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Article(
    val content: String = "",
    val likes: Int = 0,
    val title: String = "",
    @ServerTimestamp
    val date: Date = Date(),
) : Parcelable
