package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = inflater.inflate(R.layout.fragment_menu, container, false)
        val button = bind?.findViewById<Button>(R.id.button)
        button?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_fieldFragment, null)
        )
        return bind
    }
}