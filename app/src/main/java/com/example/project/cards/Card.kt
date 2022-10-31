package com.example.project.cards

data class Card(val suit: Int, val nom: Int) {
    override fun toString(): String {
        return "$suit $nom";
    }
}
