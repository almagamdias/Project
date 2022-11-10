package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class WinnerFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = inflater.inflate(R.layout.fragment_winner, container, false)
        val again = bind?.findViewById<Button>(R.id.again)
        again?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_winnerFragment_to_fieldFragment, null)
        )
        val menu = bind?.findViewById<Button>(R.id.menu)
        menu?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_winnerFragment_to_menuFragment, null)
        )
        return bind
    }
}