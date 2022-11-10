package com.example.project

import android.annotation.SuppressLint
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
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val field = Field(2)
        field.createGame()
        val bind = inflater.inflate(R.layout.fragment_field, container, false)
        val tx: TextView = bind.findViewById(R.id.text)
        tx.text = field.getPlayerCards(0)
        val tx2: TextView = bind.findViewById(R.id.text2)
        tx2.text = field.getPlayerCards(1)
        val tx3: TextView = bind.findViewById(R.id.text3)
        tx3.text = field.getSuit()
        val input: EditText = bind.findViewById(R.id.input)
        val tx4: TextView = bind.findViewById(R.id.text4)
        val tx5: TextView = bind.findViewById(R.id.text5)
        val tx6: TextView = bind.findViewById(R.id.text6)
        val bito = bind.findViewById<Button>(R.id.bito)
        val navigation = findNavController()
        if (field.isDefender(0)) {
            bito.text = "Take"
            tx6.text = "Defend!"
            field.placeBot(1)
            tx2.text = field.getPlayerCards(1)
        }
        else
            tx6.text = "Your turn!"
        input.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action !=KeyEvent.ACTION_DOWN)
                    return true
                when (keyCode) {
                    KeyEvent.KEYCODE_ENTER -> {
                        if (input.text.toString() != "") {
                            try {
                                val index = Integer.parseInt(input.text.toString()) - 1
                                if (index >= 0 && index < field.handSize(0)) {
                                    if (field.canBeat(0, index)) {
                                        tx4.text = ""
                                        field.placeInField(0, index)
                                        field.placeBot(1)
                                        tx.text = field.getPlayerCards(0)
                                        tx2.text = field.getPlayerCards(1)
                                        tx5.text = field.getField()
                                        if (field.isDefender(1)) {
                                            tx6.text = "Your Turn!"
                                            bito.text = "Bito"
                                        }
                                    }
                                    else
                                        tx4.text = "You cannot place!"
                                }
                                else
                                    tx4.text = "Invalid integer!"
                            } catch (e: Exception) {
                                tx4.text = "Null type!"
                            }
                        }
                        else
                            tx4.text = "Enter an integer!"
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
                    tx6.text = "Defend!"
                }
                else
                    tx4.text = "You cannot bito!"
            }
            if (field.isDefender(0))
                field.placeBot(1)
            tx.text = field.getPlayerCards(0)
            tx2.text = field.getPlayerCards(1)
            tx5.text = field.getField()
        }
        return bind
    }
}