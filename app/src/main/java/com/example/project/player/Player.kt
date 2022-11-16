package com.example.project.player

import com.example.project.cards.Card

class Player(private val i: Int) {
    private var attacker: Int = 0
    private val hand = mutableListOf<Card>()
    private var isWinner: Boolean = false
    private fun formatted(c: String): String {
        return c.replace(",", "").replace("[", "").replace("]", "")
    }
    fun stringHand(): String {
        hand.sortWith(compareBy({it.getNom()},{it.getSuit()}))
        var x = ""
        if (i==0)
            return formatted(hand.toString())
        else
            for (j in 0 until hand.size) {
                x += "X  "
            }
        return x
    }
    fun clearHand() {
        hand.clear()
    }
    fun clearAttack() {
        attacker = 0
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
    fun clear() {
        isWinner = false
    }
}