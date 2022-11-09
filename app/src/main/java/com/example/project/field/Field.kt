package com.example.project.field

import com.example.project.cards.Card
import com.example.project.player.Player

class Field(private val numOfPlayers: Int) {
    private val f = mutableListOf<Card>()
    private val deck = mutableListOf<Card>()
    private val p = mutableListOf<Player>()
    private val head = mutableListOf<Card>()
    fun showDeck(): String {
        return deck.toString()
    }
    fun numOfPlayers(): String {
        val x = p.size
        return "$x"
    }
    fun createGame() {
        fun createDeck() {
            for (i in 0 until 4) {
                for (j in 0 until 9) {
                    val c = Card(i, j)
                    deck.add(c)
                }
            }
        }
        for (i in 0 until 2) {
            val px = Player()
            p.add(px)
        }
        fun distribution() {
            for (i in 0 until 6) {
                for (j in 0 until numOfPlayers) {
                    p[j].getCards(deck[i * 2 + j])
                    deck.removeAt(i * 2 + j)
                }
            }
            head.add(deck[6*numOfPlayers])
            deck.removeAt(6*numOfPlayers)
            head[0].setSuit()
            p[0].setAttacker()
        }
        createDeck()
        deck.shuffle()
        distribution()
    }
    fun getSuit(): String {
        return head[0].toString()
    }
    fun getField(): String {
        return f.toString()
    }
    fun countSuit(i: Int): String {
        var x = 0
        for (j in 0 until 6) {
            if (p[i].cardsInHand(j).isHeadSuit()) {
                x++
            }
        }
        return "$x"
    }
    fun handSize(a: Int): Int {
        return p[a].handSize()
    }
    fun getPlayerCards(i: Int): String {
        p[i].sortCard()
        return p[i].stringHand()
    }
    fun canBeat(a: Int, i: Int): Boolean {
        fun check() {
            if(f.isNotEmpty()) {
                for (j in 0 until p[a].handSize()) {
                    for (k in 0 until f.size) {
                        if (f[k].getNom() == p[a].cardsInHand(j).getNom()) {
                            p[a].cardsInHand(j).setAllowedToTrue()
                        }
                    }
                }
            }
        }
        check()
        return f.isEmpty() || p[a].cardsInHand(i).isAllowed()
    }
    fun placeInField(a: Int, i: Int) {
        if (canBeat(a, i) || p[a].isAttacker()==0) {
            f.add(p[a].cardsInHand(i))
            p[a].placeCard(i)
        }
    }
    fun defend(a: Int) {
        if (p[a].isAttacker()==0) {
            var canDefend = false
            var noHead = false
            for (j in 0 until p[a].handSize()) {
                if ((p[a].cardsInHand(j).getSuit() == f[f.size - 1].getSuit())
                    && (p[a].cardsInHand(j).getNom() > f[f.size - 1].getNom())) {
                    canDefend = true
                    noHead = true
                    placeInField(a,j)
                    break
                }
            }
            if (!noHead) {
                for (j in 0 until p[a].handSize()) {
                    if (p[a].cardsInHand(j).isHeadSuit() && !f[f.size - 1].isHeadSuit()) {
                        canDefend = true
                        placeInField(a,j)
                        break
                    }
                }
            }
            if (!canDefend) {
                p[a].takeCards(f)
                f.clear()
            }
        }
    }
}