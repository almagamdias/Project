package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
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
        val tx5: TextView = bind.findViewById(R.id.text5)
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
                                    field.placeInField(0, index)
                                    tx.text = field.getPlayerCards(0)
                                    tx5.text = field.getField()
                                    field.defend(1)
                                    tx2.text = field.getPlayerCards(1)
                                    tx5.text = field.getField()
                                }
                                else
                                    tx5.text = "Invalid integer!"
                            }catch (e: Exception) {
                                tx5.text = "Null type!"
                            }
                        }
                        else
                            tx5.text = "Enter an integer!"
                    }
                }
                return false
            }
        })
        val bito = bind.findViewById<Button>(R.id.bito)
        bito?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_fieldFragment_to_loserFragment, null)
        )
        return bind
    }
}