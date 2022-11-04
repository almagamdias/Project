package com.example.project.player

import com.example.project.cards.Card

class Player() {
    private val hand = mutableListOf<Card>()
    private var isWinner: Boolean = false
    private var isLoser: Boolean = false
    fun stringHand(): String {
        return hand.toString()
    }
    fun getCards(x: Card) {
        hand.add(x)
    }
    fun cardsInHand(i: Int): Card {
        return hand[i]
    }
    fun placeCard(i: Int) {
        hand.removeAt(i)
    }
    fun takeCards(fc: MutableList<Card>) {
        hand.addAll(fc)
    }
    fun winner() {
        isWinner = true
    }
    fun loser() {
        isLoser = true
    }
}