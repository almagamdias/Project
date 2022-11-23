package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class Guide : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = inflater.inflate(R.layout.fragment_guide, container, false)
        val back = bind.findViewById<Button>(R.id.back)
        back.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_guide_to_menuFragment, null)
        )
        return bind
    }
}