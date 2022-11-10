package com.example.project.field
import com.example.project.cards.Card
import com.example.project.player.Player

class Field(private val numOfPlayers: Int) {
    private val f = mutableListOf<Card>()
    private val deck = mutableListOf<Card>()
    private val p = mutableListOf<Player>()
    private val head = mutableListOf<Card>()
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
            deck.add(deck.size-1, head[0])
            p[0].setAttacker()
            p[0].clear()
            p[1].clear()
        }
        createDeck()
        deck.shuffle()
        distribution()
    }
    fun isWinner(a: Int): Boolean {
        return p[a].isWinner()
    }
    fun getSuit(): String {
        return head[0].toString()
    }
    fun getField(): String {
        if (deck.isEmpty()) {
            if (p[0].handSize() == 0 && p[1].handSize() > 0) {
                p[0].winner()
                return "You WIN!!!"
            } else if (p[1].handSize() == 0 && p[0].handSize() > 0) {
                p[1].winner()
                return "You LOSE!!!"
            } else if (p[1].handSize() == 0 && p[0].handSize() == 0)
                return "Draw!"
        }
        return f.toString()
    }
    fun handSize(a: Int): Int {
        return p[a].handSize()
    }
    fun getPlayerCards(i: Int): String {
        p[i].sortCard()
        var x = ""
        if (i==0)
            return p[i].stringHand()
        else
            for (j in 0 until p[i].handSize()) {
                x += "X  "
            }
            return x
    }
    fun canBeat(a: Int, i: Int): Boolean {
        fun check() {
            if(f.isNotEmpty()) {
                if (p[a].isAttacker()==1) {
                    for (j in 0 until p[a].handSize()) {
                        for (k in 0 until f.size) {
                            if (f[k].getNom() == p[a].cardsInHand(j).getNom())
                                p[a].cardsInHand(j).setAllowedToTrue()
                        }
                    }
                }
                else {
                    for (j in 0 until p[a].handSize()) {
                        if (((p[a].cardsInHand(j).getSuit() == f[f.size - 1].getSuit())
                            && (p[a].cardsInHand(j).getNom() > f[f.size - 1].getNom()))
                            || (p[a].cardsInHand(j).isHeadSuit() && !f[f.size - 1].isHeadSuit())) {
                            p[a].cardsInHand(j).setAllowedToTrue()
                        }
                        else
                            p[a].cardsInHand(j).setAllowedToFalse()
                    }
                }
            }
        }
        check()
        return f.isEmpty() || p[a].cardsInHand(i).isAllowed()
    }
    fun emptyField(): Boolean {
        return f.isEmpty()
    }
    fun placeInField(a: Int, i: Int) {
        if (canBeat(a, i)) {
            f.add(p[a].cardsInHand(i))
            p[a].placeCard(i)
        }
    }
    fun takeAllCards(a: Int) {
        p[a].takeCards(f)
        f.clear()
        if (deck.isNotEmpty())
            addCard()
        for (i in 0 until numOfPlayers) {
            for (j in 0 until p[i].handSize()) {
                p[i].cardsInHand(j).setAllowedToFalse()
            }
        }
    }
    fun isDefender(a: Int): Boolean {
        return p[a].isAttacker()==0
    }
    fun placeBot(a: Int) {
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
                        placeInField(a, j)
                        break
                    }
                }
            }
            if (!canDefend) {
                p[a].takeCards(f)
                f.clear()
                if (deck.isNotEmpty())
                    addCard()
                for (i in 0 until numOfPlayers) {
                    for (j in 0 until p[i].handSize()) {
                        p[i].cardsInHand(j).setAllowedToFalse()
                    }
                }
            }
        }
        else {
            var place = false
            var noHead = false
            for (i in 0 until handSize(a)) {
                if (canBeat(a, i) && !p[a].cardsInHand(i).isHeadSuit()){
                    place = true
                    noHead = true
                    f.add(p[a].cardsInHand(i))
                    p[a].placeCard(i)
                    break
                }
            }
            if (!noHead && deck.isEmpty()) {
                for (i in 0 until handSize(a)) {
                    if (canBeat(a, i)) {
                        place = true
                        f.add(p[a].cardsInHand(i))
                        p[a].placeCard(i)
                        break
                    }
                }
            }
            if (!place)
                bito()
        }
    }
    fun bito() {
        fun nextMove() {
            for (i in 0 until numOfPlayers) {
                p[i].setAttacker()
            }
        }
        f.clear()
        if (deck.isNotEmpty())
            addCard()
        nextMove()
        for (i in 0 until numOfPlayers) {
            for (j in 0 until p[i].handSize()) {
                p[i].cardsInHand(j).setAllowedToFalse()
            }
        }
    }
    private fun addCard() {
        fun fromDeck(i: Int) {
            while (p[i].handSize() < 6) {
                p[i].getCards(deck[0])
                deck.removeAt(0)
            }
        }
        for (i in 0 until numOfPlayers) {
            if (p[i].isAttacker()==1) {
                fromDeck(i)
            }
        }
        for (i in 0 until numOfPlayers) {
            if (p[i].isAttacker()==0) {
                fromDeck(i)
            }
        }
    }
}