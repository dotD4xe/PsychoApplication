package com.example.psychoapplication.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.psychoapplication.data.model.User
import com.example.psychoapplication.data.repository.SettingsRepository
import com.example.psychoapplication.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: SettingsRepository
): ViewModel() {

    private val _updateUserInfo = MutableLiveData<UiState<User>>()
    val updateUserInfo: LiveData<UiState<User>>
        get() = _updateUserInfo

    private val _updatePassword = MutableLiveData<UiState<String>>()
    val updatePassword: LiveData<UiState<String>>
        get() = _updatePassword

    fun updateUserInfo(name: String) {
        _updateUserInfo.value = UiState.Loading
        repository.updateName(name) {
            _updateUserInfo.value = it
        }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

    fun updatePassword(email: String, oldPassword: String, newPassword: String) {
        _updatePassword.value = UiState.Loading
        repository.updatePassword(email, oldPassword, newPassword) {
            _updatePassword.value = it
        }
    }

    fun getSession(result: (User?) -> Unit){
        repository.getSession(result)
    }
}