package com.example.psychoapplication.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.psychoapplication.data.model.User
import com.example.psychoapplication.data.repository.AuthRepository
import com.example.psychoapplication.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _updateUserInfo = MutableLiveData<UiState<String>>()
    val updateUserInfo: LiveData<UiState<String>>
        get() = _updateUserInfo

    fun updateUserInfo(user: User) {
        _updateUserInfo.value = UiState.Loading
        repository.updateUserInfo(user) {
            _updateUserInfo.value = it
        }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

    fun getSession(result: (User?) -> Unit){
        repository.getSession(result)
    }

    fun updateSession(user: User, result: (User?) -> Unit) {
        repository.updateSession(user, result)
    }
}