package com.aruel.mvvmnewsapp.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil {

    fun applyTheme(context: Context) {
        val sharedPref = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("DARK_THEME", false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
