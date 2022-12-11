package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentTestBinding


class Test : Fragment() {
    private val st: Stats by activityViewModels()
    private val th = Themes()
    @SuppressLint("MissingInflatedId", "SetTextI18n", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)
        binding.lifecycleOwner = this
        st.win.observe(viewLifecycleOwner) { win ->
            binding.wins.text = "Wins: $win"
        }
        st.draw.observe(viewLifecycleOwner) { draw ->
            binding.draws.text = "Draws: $draw"
        }
        st.lose.observe(viewLifecycleOwner) { lose ->
            binding.loses.text = "Loses: $lose"
        }
        st.history.observe(viewLifecycleOwner) { his ->
            if(st.len()!=0) binding.his.text = his
        }
        binding.add.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.green.setOnClickListener{
            activity?.let { it1 -> th.changeToTheme(it1,0) }
        }
        binding.yellow.setOnClickListener{
            activity?.let { it1 -> th.changeToTheme(it1,1) }
        }
        binding.black.setOnClickListener{
            activity?.let { it1 -> th.changeToTheme(it1,2) }
        }
        binding.purple.setOnClickListener{
            activity?.let { it1 -> th.changeToTheme(it1,3) }
        }
        return binding.root
    }
}