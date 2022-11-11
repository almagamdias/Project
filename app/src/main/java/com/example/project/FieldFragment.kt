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


class FieldFragment : Fragment() {
    @SuppressLint("SetTextI18n", "MissingInflatedId", "ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val field = Field(2)
        field.createGame()
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
            opponent.text = field.getPlayerCards(1)
            you.text = field.getPlayerCards(0)
            field1.text = field.getField(0)
            field2.text = field.getField(1)
            headSuit.text = field.getSuit() + " " + field.cardsLeft()
            if (field.emptyDeck())
                headSuit.setTextColor(Color.WHITE)
            if (field.isWinner(1) || field.isWinner(0))
                bito.text = "GO!"
        }
        if (field.isDefender(0)) {
            bito.text = "Take"
            turn.text = "Defend!"
            field.placeBot(1)
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
                                if (index >= 0 && index < field.handSize(0) && !field.isWinner(1)) {
                                    if (field.canBeat(0, index)) {
                                        hint.text = ""
                                        field.placeInField(0, index)
                                        field.placeBot(1)
                                        if (field.isDefender(1)) {
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
            if (field.isWinner(1))
                navigation.navigate(R.id.action_fieldFragment_to_loserFragment)
            else if (field.isWinner(0))
                navigation.navigate(R.id.action_fieldFragment_to_winnerFragment)
            if (field.isDefender(0)) {
                field.takeAllCards(0)
            }
            else {
                if (!field.emptyField()) {
                    field.bito()
                    bito.text = "Take"
                    turn.text = "Defend!"
                }
                else
                    hint.text = "You cannot bito!"
            }
            if (field.isDefender(0))
                field.placeBot(1)
            update()
        }
        return bind
    }
}