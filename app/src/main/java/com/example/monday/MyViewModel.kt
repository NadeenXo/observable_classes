package com.example.monday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _tvLiveData = MutableLiveData<String>()
    val tvLiveData: LiveData<String> = _tvLiveData

    private val _tvStateFlow = MutableStateFlow("State Flow")
    val tvStateFlow = _tvStateFlow.asStateFlow()

    private val _tvSharedFlow = MutableSharedFlow<String>()
    val tvSharedFlow = _tvSharedFlow.asSharedFlow()


    fun setTvLiveData(text: String) {
        _tvLiveData.value = text
    }

//    fun setTvLiveData(text: String) {
//        _tvLiveData.postValue(text) // used in background thread or corotienes
//    }

    fun setTvStateFlow(text: String) {
        _tvStateFlow.value = text
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _tvSharedFlow.emit("Shared Flow")
        }
    }

    fun triggerFlow(): Flow<String> {
        return flow {
            repeat(5) {
                emit("Flow $it")
                delay(1000L)
            }
        }
    }


}