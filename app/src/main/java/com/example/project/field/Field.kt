package com.example.project.field

import com.example.project.cards.Card
import com.example.project.player.Player

class Field(private val numOfPlayers: Int) {
    private val f = mutableListOf<Card>();
    private val deck = mutableListOf<Card>();
    private val p = mutableListOf<Player>();
    fun showDeck(): String {
        return deck.toString();
    }
    fun createDeck() {
        for (i in 0 until 9) {
            for (j in 0 until 4) {
                val c = Card(i, j);
                deck.add(c);
            }
        }
        deck.shuffle();
    }
    fun createGame() {
        for (i in  0 until 2) {
            val px = Player(i);
            p.add(px);
        }
    }
    fun numOfPlayers(): String {
        val x = p.size;
        return "$x";
    }
    fun distribution() {
        for (i in 0 until 6) {
            for (j in 0 until numOfPlayers) {
                p[j].getCards(deck[i * 2 + j]);
                deck.removeAt(i * 2 + j);
            }
        }
    }
    fun getPlayerCards(i: Int): String {
        val t = p[i].cardsInHand().toString();
        return t;
    }
}