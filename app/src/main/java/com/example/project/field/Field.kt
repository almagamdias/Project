package com.example.project.field
import com.example.project.cards.Card
import com.example.project.player.Player

class Field {
    private val f = mutableListOf<Card>()
    private val deck = mutableListOf<Card>()
    private val head = mutableListOf<Card>()
    private val bito = mutableListOf<Card>()
    private var alarm = false
    private fun addDeck(c: List<Card>) {
        deck.addAll(c)
    }
    fun createGame(p: List<Player>, c: MutableList<Card>) {
        fun clearGame() {
            deck.clear()
            head.clear()
            f.clear()
            bito.clear()
            for (i in p.indices) {
                p[i].clearAttack()
                p[i].clearHand()
            }
        }
        fun distribution() {
            for (i in 0 until 6) {
                for (j in p.indices) {
                    p[j].getCards(deck[0])
                    deck.removeAt(0)
                }
            }
            head.add(deck[0])
            deck.removeAt(0)
            head[0].setSuit()
            deck.add(deck.size-1, head[0])
            if (p[0].isWinner() && !p[1].isWinner())
                p[0].setAttacker()
            else if (p[1].isWinner() && !p[0].isWinner())
                p[1].setAttacker()
            else
                p[0].setAttacker()
            p[0].clear()
            p[1].clear()
        }
        clearGame()
        addDeck(c)
        deck.shuffle()
        distribution()
    }
    fun getSuit(): String {
        return head[0].toString()
    }
    fun cardsLeft(): String {
        val l = deck.size
        return " [$l]"
    }
    fun getField(e: Int): String {
        var x1 = ""
        var x2 = ""
        return if (f.isEmpty())
            " "
        else {
            for (i in 0 until f.size) {
                if (i % 2 == 0)
                    x1+=f[i].toString()+" "
                else
                    x2+=f[i].toString()+" "
            }
            if (e==0)
                x1
            else
                x2
        }
    }
    private fun formatted(c: String): String {
        return c.replace(",", "").replace("[", "").replace("]", "")
    }
    fun canBeat(p: Player, i: Int): Boolean {
        fun check() {
            if(f.isNotEmpty()) {
                if (p.isAttacker()==1) {
                    for (j in 0 until p.handSize()) {
                        for (k in 0 until f.size) {
                            if (f[k].getNom() == p.cardsInHand(j).getNom())
                                p.cardsInHand(j).setAllowedToTrue()
                        }
                    }
                }
                else {
                    for (j in 0 until p.handSize()) {
                        if (((p.cardsInHand(j).getSuit() == f[f.size - 1].getSuit())
                            && (p.cardsInHand(j).getNom() > f[f.size - 1].getNom()))
                            || (p.cardsInHand(j).isHeadSuit() && !f[f.size - 1].isHeadSuit())) {
                            p.cardsInHand(j).setAllowedToTrue()
                        }
                        else
                            p.cardsInHand(j).setAllowedToFalse()
                    }
                }
            }
        }
        check()
        return f.isEmpty() || p.cardsInHand(i).isAllowed()
    }
    fun emptyDeck(): Boolean {
        return deck.isEmpty()
    }
    fun fieldSize(): Int {
        return f.size
    }
    fun placeInField(p: Player, i: Int) {
        alarm = false
        if (canBeat(p, i)) {
            f.add(p.cardsInHand(i))
            p.placeCard(i)
        }
    }
    fun isTaking(): Boolean {
        return alarm
    }
    fun takeAllCards(p: Player, px: List<Player>) {
        alarm = true
        p.takeCards(f)
        f.clear()
        addCard(px)
    }
    fun placeBot(p: Player, px: List<Player>) {
        if (p.isAttacker()==0) {
            var canDefend = false
            var noHead = false
            for (j in 0 until p.handSize()) {
                if ((p.cardsInHand(j).getSuit() == f[f.size - 1].getSuit())
                    && (p.cardsInHand(j).getNom() > f[f.size - 1].getNom())) {
                    canDefend = true
                    noHead = true
                    placeInField(p, j)
                    break
                }
            }
            if (!noHead) {
                for (j in 0 until p.handSize()) {
                    if (p.cardsInHand(j).isHeadSuit() && !f[f.size - 1].isHeadSuit()) {
                        canDefend = true
                        placeInField(p, j)
                        break
                    }
                }
            }
            if (!canDefend) {
                takeAllCards(p, px)
            }
        }
        else {
            var place = false
            var noHead = false
            for (i in 0 until p.handSize()) {
                if (canBeat(p, i) && !p.cardsInHand(i).isHeadSuit()){
                    place = true
                    noHead = true
                    f.add(p.cardsInHand(i))
                    p.placeCard(i)
                    break
                }
            }
            if (!noHead && deck.size < 4) {
                for (i in 0 until p.handSize()) {
                    if (canBeat(p, i)) {
                        place = true
                        f.add(p.cardsInHand(i))
                        p.placeCard(i)
                        break
                    }
                }
            }
            if (!place)
                bito(px)
        }
    }
    fun emptyHistory(): Boolean {
        return bito.isEmpty()
    }
    fun history(): String {
        return formatted(bito.toString())
    }
    fun bito(p: List<Player>) {
        fun nextMove() {
            for (element in p) {
                element.setAttacker()
            }
        }
        bito.addAll(f)
        f.clear()
        addCard(p)
        nextMove()
    }
    private fun addCard(p: List<Player>) {
        fun fromDeck(i: Int) {
            while (p[i].handSize() < 6 && deck.isNotEmpty()) {
                p[i].getCards(deck[0])
                deck.removeAt(0)
            }
        }
        for (i in p.indices) {
            if (p[i].isAttacker() == 1) {
                fromDeck(i)
            }
        }
        for (i in p.indices) {
            if (p[i].isAttacker() == 0) {
                fromDeck(i)
            }
        }
        clearCard(p)
    }
    private fun clearCard(p: List<Player>) {
        for (i in p.indices) {
            for (j in 0 until p[i].handSize()) {
                p[i].cardsInHand(j).setAllowedToFalse()
            }
        }
    }
}