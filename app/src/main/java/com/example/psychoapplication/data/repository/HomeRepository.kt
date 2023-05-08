package com.example.psychoapplication.data.repository

import com.example.psychoapplication.data.model.Information
import com.example.psychoapplication.util.UiState

interface HomeRepository {
    fun getInformation(type: String, result: (UiState<Information?>) -> Unit)
}