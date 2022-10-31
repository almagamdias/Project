package com.example.project

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project.field.Field

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val field = Field(2);
        field.createGame();

        setContentView(R.layout.activity_main)
        val tx: TextView = findViewById(R.id.text)
        tx.setText(field.showDeck());
    }
}