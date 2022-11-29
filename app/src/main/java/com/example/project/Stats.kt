package com.example.project

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Stats : ViewModel() {
    private var name = MutableLiveData(0)
    val win: LiveData<Int>
        get() = name
    fun setName() {
        name.value = name.value?.plus(1)
    }
}