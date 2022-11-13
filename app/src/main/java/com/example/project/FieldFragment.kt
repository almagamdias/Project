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
import androidx.navigation.fragment.findNavController
import com.example.project.field.Field
import com.example.project.player.Player


class FieldFragment : Fragment() {
    private val p1 = Player(0)
    private val p2 = Player(1)
    private val p = listOf(p1, p2)
    @SuppressLint("SetTextI18n", "MissingInflatedId", "ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val field = Field()
        field.createGame(p)
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
        val navigation = findNavController()
        fun update() {
            if (!field.emptyHistory())
                history.text = field.history()
            opponent.text = p2.stringHand()
            you.text = p1.stringHand()
            field1.text = field.getField(0)
            field2.text = field.getField(1)
            headSuit.text = field.getSuit() + " " + field.cardsLeft()
            if (field.emptyDeck())
                headSuit.setTextColor(Color.WHITE)
            if (p2.isWinner() || p1.isWinner())
                bito.text = "GO!"
        }
        if (p1.isAttacker()==0) {
            bito.text = "Take"
            turn.text = "Defend!"
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
                                if (index >= 0 && index < p1.handSize() && !p2.isWinner()) {
                                    if (field.canBeat(p1, index)) {
                                        hint.text = ""
                                        field.placeInField(p1, index)
                                        field.placeBot(p2, p)
                                        if (p2.isAttacker()==0) {
                                            if (field.emptyField()) {
                                                if (field.isTaking())
                                                    hint.text = "Opponent has taken cards!"
                                                else
                                                    hint.text = "Opponent has bito!"
                                            }
                                            turn.text = "Your turn!"
                                            bito.text = "Bito"
                                        }
                                        input.text.clear()
                                        update()
                                    }
                                    else
                                        hint.text = "You cannot place!"
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
            if (p2.isWinner())
                navigation.navigate(R.id.action_fieldFragment_to_loserFragment)
            else if (p1.isWinner())
                navigation.navigate(R.id.action_fieldFragment_to_winnerFragment)
            if (p1.isAttacker()==0) {
                field.takeAllCards(p1, p)
            }
            else {
                if (!field.emptyField()) {
                    field.bito(p)
                    bito.text = "Take"
                    turn.text = "Defend!"
                }
                else
                    hint.text = "You cannot bito!"
            }
            if (p1.isAttacker()==0)
                field.placeBot(p2, p)
            update()
        }
        return bind
    }
}