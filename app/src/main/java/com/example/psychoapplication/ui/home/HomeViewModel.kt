package com.example.psychoapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.psychoapplication.data.model.Information
import com.example.psychoapplication.data.repository.HomeRepository
import com.example.psychoapplication.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel() {

    private val _informations = MutableLiveData<UiState<Information?>>()
    val informations: LiveData<UiState<Information?>>
        get() = _informations

    fun getInfo(type: String) {
        _informations.value = UiState.Loading
        repository.getInformation(type) { _informations.value = it }
    }
}