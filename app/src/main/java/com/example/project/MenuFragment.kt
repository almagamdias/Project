package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.project.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bind: FragmentMenuBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        bind.lifecycleOwner = this
        bind.play.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_fieldFragment, null)
        )
        bind.toGuide.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_guide, null)
        )
        bind.toTest.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_test, null)
        )
        return bind.root
    }
}