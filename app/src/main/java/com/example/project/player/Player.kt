package com.example.project.player

import com.example.project.cards.Card

class Player {
    private var attacker: Int = 0
    private val hand = mutableListOf<Card>()
    private var isWinner: Boolean = false
    fun stringHand(): String {
        return hand.toString()
    }
    fun getCards(x: Card) {
        hand.add(x)
    }
    fun handSize(): Int {
        return hand.size
    }
    fun cardsInHand(i: Int): Card {
        return hand[i]
    }
    fun sortCard() {
        return hand.sortWith(compareBy({it.getSuit()},{it.getNom()}))
    }
    fun placeCard(i: Int) {
        hand.removeAt(i)
    }
    fun takeCards(fc: MutableList<Card>) {
        hand.addAll(fc)
    }
    fun setAttacker() {
        attacker = if (attacker==0)
            1
        else
            0
    }
    fun isAttacker(): Int {
        return attacker
    }
    fun isWinner(): Boolean {
        return isWinner
    }
    fun winner() {
        isWinner = true
    }
}