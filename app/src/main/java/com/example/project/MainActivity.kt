package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project.field.Field
import com.example.project.player.Player

class MainActivity : AppCompatActivity() {
    private val p = mutableListOf<Player>();
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val field = Field(2);
        field.createGame();
        field.createDeck();
        //field.distribution();
        setContentView(R.layout.activity_main)
        val tx: TextView = findViewById(R.id.text)
        tx.setText(field.showDeck());
        val tx2: TextView = findViewById(R.id.text2)
        tx2.setText(field.numOfPlayers());
    }
}