package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBindings
import com.example.project.cards.Card
import com.example.project.databinding.FragmentTestBinding

class Test : Fragment() {
    private val st: Stats by activityViewModels()
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)
        /*val s: LinearLayout = bind.findViewById(R.id.spades)
        deck(s, 0)
        val h: LinearLayout = bind.findViewById(R.id.hearts)
        deck(h, 1)
        val d: LinearLayout = bind.findViewById(R.id.diamonds)
        deck(d, 2)
        val c: LinearLayout = bind.findViewById(R.id.clubs)
        deck(c, 3)*/
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
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_test_to_menuFragment)
        }
        return binding.root
    }
    private fun deck(r: LinearLayout, i: Int): MutableList<Card> {
        val d = mutableListOf<Card>()
        val x = mutableListOf<ImageView>()
            for (j in 0 until 9) {
                val c = Card(i,j)
                val img = ImageView(context)
                when(i) {
                    0 -> when(j) {
                        0 -> img.setImageResource(R.drawable.spade_6)
                        1 -> img.setImageResource(R.drawable.spade_7)
                        2 -> img.setImageResource(R.drawable.spade_8)
                        3 -> img.setImageResource(R.drawable.spade_9)
                        4 -> img.setImageResource(R.drawable.spade_10)
                        5 -> img.setImageResource(R.drawable.spade_j)
                        6 -> img.setImageResource(R.drawable.spade_q)
                        7 -> img.setImageResource(R.drawable.spade_k)
                        8 -> img.setImageResource(R.drawable.spade_a)
                    }
                    1 -> when(j) {
                        0 -> img.setImageResource(R.drawable.heart_6)
                        1 -> img.setImageResource(R.drawable.heart_7)
                        2 -> img.setImageResource(R.drawable.heart_8)
                        3 -> img.setImageResource(R.drawable.heart_9)
                        4 -> img.setImageResource(R.drawable.heart_10)
                        5 -> img.setImageResource(R.drawable.heart_j)
                        6 -> img.setImageResource(R.drawable.heart_q)
                        7 -> img.setImageResource(R.drawable.heart_k)
                        8 -> img.setImageResource(R.drawable.heart_a)
                    }
                    2 -> when(j) {
                        0 -> img.setImageResource(R.drawable.diamond_6)
                        1 -> img.setImageResource(R.drawable.diamond_7)
                        2 -> img.setImageResource(R.drawable.diamond_8)
                        3 -> img.setImageResource(R.drawable.diamond_9)
                        4 -> img.setImageResource(R.drawable.diamond_10)
                        5 -> img.setImageResource(R.drawable.diamond_j)
                        6 -> img.setImageResource(R.drawable.diamond_q)
                        7 -> img.setImageResource(R.drawable.diamond_k)
                        8 -> img.setImageResource(R.drawable.diamond_a)
                    }
                    3 -> when(j) {
                        0 -> img.setImageResource(R.drawable.club_6)
                        1 -> img.setImageResource(R.drawable.club_7)
                        2 -> img.setImageResource(R.drawable.club_8)
                        3 -> img.setImageResource(R.drawable.club_9)
                        4 -> img.setImageResource(R.drawable.club_10)
                        5 -> img.setImageResource(R.drawable.club_j)
                        6 -> img.setImageResource(R.drawable.club_q)
                        7 -> img.setImageResource(R.drawable.club_k)
                        8 -> img.setImageResource(R.drawable.club_a)
                    }
                }
                d.add(c)
                r.addView(img)
            }
        return d
    }
}