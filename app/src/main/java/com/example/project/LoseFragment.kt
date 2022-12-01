package com.example.project

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class LoseFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Game over")
                .setMessage("Yow lose!!")
                .setCancelable(true)
                .setPositiveButton("Again") { _, _ ->
                    Toast.makeText(
                        activity,
                        "Try again",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_loseFragment_to_fieldFragment, null)
                }
                .setNegativeButton(
                    "Menu"
                ) { _, _ ->
                    Toast.makeText(
                        activity, "I am tried",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_loseFragment_to_menuFragment, null)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}