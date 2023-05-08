package com.example.psychoapplication.data.repository

import com.example.psychoapplication.data.model.Information
import com.example.psychoapplication.util.FireStoreCollection
import com.example.psychoapplication.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

class HomeRepositoryImp (
    private val database: FirebaseFirestore,
) : HomeRepository {

    override fun getInformation(type: String, result: (UiState<Information?>) -> Unit) {
        database.collection(FireStoreCollection.INFORMATION)
            .document(type)
            .get()
            .addOnSuccessListener {
                val document = it.toObject(Information::class.java)
                result.invoke(
                    UiState.Success(document)
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