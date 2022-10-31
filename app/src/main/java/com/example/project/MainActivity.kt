package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project.field.Field

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val field = Field(2);
        field.createGame();
        setContentView(R.layout.activity_main)
        val tx: TextView = findViewById(R.id.text)
        tx.text = field.getPlayerCards(0);
        val tx2: TextView = findViewById(R.id.text2)
        tx2.text = field.getPlayerCards(1);
        val tx3: TextView = findViewById(R.id.text3)
        tx3.text = field.getSuit();
        val tx4: TextView = findViewById(R.id.text4)
        tx4.text = field.showDeck();
        val tx5: TextView = findViewById(R.id.text5)
        tx5.text = field.countSuit(0) + " " + field.countSuit(1);
    }
}