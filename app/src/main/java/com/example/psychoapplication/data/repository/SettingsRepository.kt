package com.example.psychoapplication.data.repository

import com.example.psychoapplication.data.model.User
import com.example.psychoapplication.util.UiState

interface SettingsRepository {
    fun updateName(name: String, result: (UiState<User>) -> Unit)
    fun updatePassword(email: String, oldPassword: String, newPassword: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
    fun getSession(result: (User?) -> Unit)
}