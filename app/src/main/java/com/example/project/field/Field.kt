package com.example.project.field

import com.example.project.player.Player

class Field(private val numOfPlayers: Int) {
    private val f = mutableListOf<Int>();
    fun createGame() {
        for (i in  0 until numOfPlayers) {
            val p = Player(i);
        }
    }
}