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
        //val index: Int = toInteger(input.text.toString());
        //field.placeInField(0, index);
        val tx5: TextView = bind.findViewById(R.id.text5)
        input.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    tx5.text = input.text.toString();
                    return true
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
    private fun toInteger(s: String): Int {
        return s.toInt();
    }
}