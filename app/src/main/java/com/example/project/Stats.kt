package com.example.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Stats : ViewModel() {
    private var _win = MutableLiveData(0)
    private var _draw = MutableLiveData(0)
    private var _lose = MutableLiveData(0)
    private var his = MutableLiveData("")
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
    val history: LiveData<String>
        get() = his
    fun addHis(x: Int) {
        if (len()!! >= 13)
            his.value = his.value?.drop(3)
        when (x) {
            0 -> his.value = his.value.plus(" W ")
            1 -> his.value = his.value.plus(" D ")
            2 -> his.value = his.value.plus(" L ")
        }
    }
    fun len(): Int? {
        return his.value?.length
    }
}