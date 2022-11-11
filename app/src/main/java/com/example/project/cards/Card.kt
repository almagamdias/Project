package com.example.project.cards

class Card(private val suit: Int, private val nom: Int) {
    companion object {
        private var headSuit: Int = -1
    }
    private var isAllowed = false
    override fun toString(): String {
        var realSuit = ""
        var realNom = ""
        when (suit) {
            0 -> realSuit = "♠"
            1 -> realSuit = "♥"
            2 -> realSuit = "♣"
            3 -> realSuit = "♦"
        }
        when (nom) {
            0 -> realNom = "6 "
            1 -> realNom = "7 "
            2 -> realNom = "8 "
            3 -> realNom = "9 "
            4 -> realNom = "10"
            5 -> realNom = "J "
            6 -> realNom = "Q "
            7 -> realNom = "K "
            8 -> realNom = "A "
        }
        return realSuit + realNom
    }
    fun setSuit() {
        headSuit = suit
    }
    fun isHeadSuit(): Boolean {
        return suit == headSuit
    }
    fun getSuit(): Int {
        return suit
    }
    fun getNom(): Int {
        return nom
    }
    fun isAllowed(): Boolean {
        return isAllowed
    }
    fun setAllowedToTrue() {
        isAllowed = true
    }
    fun setAllowedToFalse() {
        isAllowed = false
    }
}
