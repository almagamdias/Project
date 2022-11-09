package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class MenuFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = inflater.inflate(R.layout.fragment_menu, container, false)
        val play = bind.findViewById<Button>(R.id.play)
        play.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_fieldFragment, null)
        )
        return bind
    }
}