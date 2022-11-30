package com.example.project

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Stats(private val state: SavedStateHandle) : ViewModel() {
    private var _win = MutableLiveData(0)
    private var _draw = MutableLiveData(0)
    private var _lose = MutableLiveData(0)
    val win: LiveData<Int>
        get() = _win
    val draw: LiveData<Int>
        get() = _draw
    val lose: LiveData<Int>
        get() = _lose
    fun plusWin() {
        _win.value = _win.value?.plus(1)
    }
    fun plusDraw() {
        _draw.value = _draw.value?.plus(1)
    }
    fun plusLose() {
        _lose.value = _lose.value?.plus(1)
    }
}