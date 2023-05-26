package com.example.psychoapplication.data.repository

import android.content.SharedPreferences
import com.example.psychoapplication.data.model.User
import com.example.psychoapplication.util.FireStoreCollection
import com.example.psychoapplication.util.SharedPrefConstants
import com.example.psychoapplication.util.UiState
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class SettingsRepositoryImp(
    val auth: FirebaseAuth,
    private val database: FirebaseFirestore,
    private val appPreferences: SharedPreferences,
    private val gson: Gson
): SettingsRepository {

    override fun updateName(name: String, result: (UiState<User>) -> Unit) {
        val userStr = appPreferences.getString(SharedPrefConstants.USER_SESSION, null)
        if (userStr == null) {
            result.invoke( UiState.Failure("Bad") )
        } else {
            val user = gson.fromJson(userStr, User::class.java)
            val updateUser = user.copy(name = name)
            appPreferences.edit().putString(SharedPrefConstants.USER_SESSION, gson.toJson(updateUser))
                .apply()
            val document = database.collection(FireStoreCollection.USER).document(updateUser.id)
            document
                .set(updateUser)
                .addOnSuccessListener { result.invoke(UiState.Success(updateUser)) }
                .addOnFailureListener { result.invoke(UiState.Failure("Bad")) }
        }
    }

    override fun updatePassword(email: String, oldPassword: String, newPassword: String, result: (UiState<String>) -> Unit) {
        val user = auth.currentUser
        val authCredential = EmailAuthProvider.getCredential(email, oldPassword)
        if (user != null) {
            user.reauthenticate(authCredential)
                .addOnSuccessListener {
                    user.updatePassword(newPassword)
                        .addOnSuccessListener {
                            //password updated
                            result.invoke(UiState.Success("Success"))
                        }
                        .addOnFailureListener { e ->
                            result.invoke(UiState.Failure(e.message))
                        }
                }
                .addOnFailureListener { e ->
                    result.invoke(UiState.Failure(e.message))
                }
        }
        else UiState.Failure("bad")
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        appPreferences.edit().putString(SharedPrefConstants.USER_SESSION, null).apply()
        result.invoke()
    }

    override fun getSession(result: (User?) -> Unit) {
        val userStr = appPreferences.getString(SharedPrefConstants.USER_SESSION, null)
        if (userStr == null) {
            result.invoke(null)
        } else {
            val user = gson.fromJson(userStr, User::class.java)
            result.invoke(user)
        }
    }

}