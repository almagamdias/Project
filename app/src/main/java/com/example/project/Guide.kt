package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Guide : Fragment() {
    private val tx = "How to play?\n\n"
    private val t1 = "Fool game (also known as Durak) is so popular in CIS. That is you should learn to play if you are beginner.\n"
    private val t2 = "First of all it has 36 cards in deck. There are only one deck. Then after shuffling distribute to players (up to 6 cards to each player), and the next card is trump suit and lies opened, then deck lies over the trump card.\n"
    private val t3 = "The game starts. First turn get the player if he has the smallest trump card(If you won, then you can turn first). If defender has a bigger by nominal card or trump(if an attacking card is not trump), he can defend, else took all cards in field. After that if attacker has cards with nominal equal to field cards, he can attack again, else all field cards is away from field(Bito) and Attacker/Defender is changed each other. After player has taken all field cards or away from it, they get cards from deck(First attacker, then defender) while the number of cards in hand is less than 6.\n"
    private val t4 = "If the player has not any cards in hand and the deck is empty, he is winner. Then if there are only one player with cards, he is loser fool. But if the players has not cards, it is draw.\n"
    private var strings = arrayOf(tx, t1, t2, t3, t4)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rv = RecyclerView(requireContext())
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = GuideRVAdapter(strings)
        return rv
    }

    class GuideRVAdapter(private val data: Array<String>) :
        RecyclerView.Adapter<GuideViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): GuideViewHolder {
            val view: View = TextView(parent.context)
            return GuideViewHolder(view)
        }
        override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
            holder.textView.text = data[position]
            holder.textView.textSize = 22.0F
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }

    class GuideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView

        init {
            textView = itemView as TextView
        }
    }
}