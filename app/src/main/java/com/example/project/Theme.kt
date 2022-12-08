package com.example.project

import android.app.Activity

import android.content.Intent

class Themes {
    companion object {
        private var sTheme = 0
    }
    private val p1 = 0
    private val p2 = 1
    private val p3 = 2

    fun changeToTheme(activity: Activity, theme: Int) {
        sTheme = theme
        activity.finish()
        activity.startActivity(Intent(activity, activity.javaClass))
    }

    fun onActivityCreateSetTheme(activity: Activity) {
        when (sTheme) {
            p1 -> activity.setTheme(R.style.Theme_Project)
            p2 -> activity.setTheme(R.style.Theme_Project2)
            p3 -> activity.setTheme(R.style.Theme_Project3)
            else -> activity.setTheme(R.style.Theme_Project4)
        }
    }
}