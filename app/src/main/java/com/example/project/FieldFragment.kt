package com.example.project

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.project.cards.Card
import com.example.project.field.Field
import com.example.project.player.Player


class FieldFragment : Fragment() {
    private val p1 = Player(0)
    private val p2 = Player(1)
    private val p = listOf(p1, p2)
    private val field = Field()
    private fun deck(): MutableList<Card> {
        val d = mutableListOf<Card>()
        for (i in 0 until 4) {
            for (j in 0 until 9) {
                val c = Card(i,j)
                d.add(c)
            }
        }
        return d
    }
    private val cd = deck()
    @SuppressLint("SetTextI18n", "MissingInflatedId", "ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = inflater.inflate(R.layout.fragment_field, container, false)
        val history: TextView = bind.findViewById(R.id.history)
        val opponent: TextView = bind.findViewById(R.id.opponent)
        val you: TextView = bind.findViewById(R.id.you)
        val headSuit: TextView = bind.findViewById(R.id.head_suit)
        val input: EditText = bind.findViewById(R.id.input)
        val hint: TextView = bind.findViewById(R.id.hint)
        val field1: TextView = bind.findViewById(R.id.field1)
        val field2: TextView = bind.findViewById(R.id.field2)
        val turn: TextView = bind.findViewById(R.id.turn)
        val bito = bind.findViewById<Button>(R.id.bito)
        field.createGame(p, cd)
        fun update() {
            if (!field.emptyHistory())
                history.text = field.history()
            opponent.text = p2.stringHand()
            you.text = p1.stringHand()
            field1.text = field.getField(0)
            field2.text = field.getField(1)
            headSuit.setTextColor(Color.BLACK)
            headSuit.text = field.getSuit() + " " + field.cardsLeft()
            if (field.emptyDeck()) {
                headSuit.setTextColor(Color.WHITE)
                gameOver(field1, field2)
            }
            if (p2.isWinner() || p1.isWinner())
                bito.text = "Again!"
            if (p2.isAttacker()==1) {
                bito.text = "Take"
                turn.text = "Defend!"
            }
            else if (p1.isAttacker()==1){
                turn.text = "Your turn!"
                bito.text = "Bito"
            }
        }
        if (p1.isAttacker()==0) {
            field.placeBot(p2, p)
        }
        update()
        input.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action !=KeyEvent.ACTION_DOWN)
                    return true
                when (keyCode) {
                    KeyEvent.KEYCODE_ENTER -> {
                        if (input.text.toString() != "") {
                            try {
                                val index = Integer.parseInt(input.text.toString()) - 1
                                if (index >= 0 && index < p1.handSize() && !p2.isWinner() && !p1.isWinner()) {
                                    if (field.canBeat(p1, index)) {
                                        hint.text = ""
                                        field.placeInField(p1, index)
                                        field.placeBot(p2, p)
                                        if (p2.isAttacker()==0) {
                                            if (field.fieldSize()==0) {
                                                if (field.isTaking())
                                                    hint.text = "Opponent has taken cards!"
                                                else
                                                    hint.text = "Opponent has bito!"
                                            }
                                        }
                                        input.text.clear()
                                        update()
                                    }
                                    else
                                        hint.text = "You cannot place!"
                                }
                                else if(p1.isWinner()) {
                                    hint.text = "You win!"
                                }
                                else if(p2.isWinner()) {
                                    hint.text = "You lose!"
                                }
                                else
                                    hint.text = "Invalid integer!"
                            } catch (e: Exception) {
                                hint.text = "Null type!"
                            }
                        }
                        else
                            hint.text = "Enter an integer!"
                    }
                }
                return false
            }
        })
        bito.setOnClickListener {
            if (p2.isWinner() || p1.isWinner())
                field.createGame(p, cd)
                history.text = ""
            if (p1.isAttacker()==0)
                field.takeAllCards(p1, p)
            else {
                if (field.fieldSize()!=0)
                    field.bito(p)
                else
                    hint.text = "You cannot bito!"
            }
            if (p1.isAttacker()==0)
                field.placeBot(p2, p)
            update()
        }
        return bind
    }
    @SuppressLint("SetTextI18n")
    fun gameOver(t: TextView, t2: TextView) {
        for (i in p.indices) {
            if (p[i].handSize()==0) {
                p[i].winner()
                if (i==0)
                    t.text = "You are Winner!"
                else if (i==1)
                    t.text = "Opponent is Winner!"
                t2.text = ""
            }
        }
        if (p1.isWinner() && p2.isWinner()) {
            t.text = "Draw!"
        }
    }
}