package com.example.project.field
import com.example.project.cards.Card
import com.example.project.player.Player

class Field(private val numOfPlayers: Int) {
    private val f = mutableListOf<Card>()
    private val deck = mutableListOf<Card>()
    private val p = mutableListOf<Player>()
    private val head = mutableListOf<Card>()
    private val bito = mutableListOf<Card>()
    private var alarm = false
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
    fun cardsLeft(): String {
        val l = deck.size
        return " [$l]"
    }
    fun getField(e: Int): String {
        var x1 = ""
        var x2 = ""
        if (deck.isEmpty()) {
            if (p[0].handSize() == 0 && p[1].handSize() > 0) {
                p[0].winner()
                return if(e==0)
                    "You WIN!!!"
                else
                    ""
            } else if (p[1].handSize() == 0 && p[0].handSize() > 0) {
                p[1].winner()
                return if(e==0)
                    "You LOSE!!!"
                else
                    ""
            } else if (p[1].handSize() == 0 && p[0].handSize() == 0) {
                p[0].winner()
                p[1].winner()
                return if(e==0)
                    "DRAW!!!"
                else
                    ""
            }
        }
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
    fun handSize(a: Int): Int {
        return p[a].handSize()
    }
    fun getPlayerCards(i: Int): String {
        p[i].sortCard()
        var x = ""
        if (i==0)
            return formatted(p[i].stringHand())
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
    fun emptyDeck(): Boolean {
        return deck.isEmpty()
    }
    fun placeInField(a: Int, i: Int) {
        alarm = false
        if (canBeat(a, i)) {
            f.add(p[a].cardsInHand(i))
            p[a].placeCard(i)
        }
    }
    fun isTaking(): Boolean {
        return alarm
    }
    fun takeAllCards(a: Int) {
        alarm = true
        p[a].takeCards(f)
        f.clear()
        addCard()
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
                takeAllCards(a)
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
            if (!noHead && deck.size < 4) {
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
    fun emptyHistory(): Boolean {
        return bito.isEmpty()
    }
    fun history(): String {
        return formatted(bito.toString())
    }
    fun bito() {
        fun nextMove() {
            for (i in 0 until numOfPlayers) {
                p[i].setAttacker()
            }
        }
        bito.addAll(f)
        f.clear()
        addCard()
        nextMove()
    }
    private fun addCard() {
        fun fromDeck(i: Int) {
            while (p[i].handSize() < 6 && deck.isNotEmpty()) {
                p[i].getCards(deck[0])
                deck.removeAt(0)
            }
        }
        for (i in 0 until numOfPlayers) {
            if (p[i].isAttacker() == 1) {
                fromDeck(i)
            }
        }
        for (i in 0 until numOfPlayers) {
            if (p[i].isAttacker() == 0) {
                fromDeck(i)
            }
        }
        for (i in 0 until numOfPlayers) {
            for (j in 0 until p[i].handSize()) {
                p[i].cardsInHand(j).setAllowedToFalse()
            }
        }
    }
}