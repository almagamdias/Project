package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val tx4: TextView = bind.findViewById(R.id.text4)
        tx4.text = field.showDeck()
        val tx5: TextView = bind.findViewById(R.id.text5)
        tx5.text = field.countSuit(0) + " " + field.countSuit(1)
        val bito = bind?.findViewById<Button>(R.id.bito)
        bito?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_fieldFragment_to_loserFragment, null)
        )
        return bind
    }
}