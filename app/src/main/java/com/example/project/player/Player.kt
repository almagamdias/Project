package com.example.project.player

import com.example.project.cards.Card

class Player(val index: Int) {
    private val hand = mutableListOf<Card>();
    private val i: Int = 0;
    private var isWinner: Boolean = false;
    private var isLoser: Boolean = false;
    fun getCards(x: Card) {
        hand.add(x)
    }
    fun placeCard() {
        hand.removeAt(i)
    }
    fun takeCards(fc: MutableList<Card>) {
        hand.addAll(fc);
    }
    fun winner() {
        isWinner = true;
    }
    fun loser() {
        isLoser = true;
    }
}