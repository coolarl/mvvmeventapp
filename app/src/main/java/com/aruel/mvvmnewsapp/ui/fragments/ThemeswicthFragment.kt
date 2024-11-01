package com.aruel.mvvmnewsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.aruel.mvvmnewsapp.R

class ThemeswicthFragment : Fragment(R.layout.fragment_themeswicth) {

    private lateinit var themeSwitch: Switch

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeSwitch = view.findViewById(R.id.switch_theme)

        // Load saved theme preference
        val sharedPref = activity?.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref?.getBoolean("DARK_THEME", false) ?: false
        themeSwitch.isChecked = isDarkMode

        // Switch listener to change theme
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            setTheme(isChecked)
        }
    }

    private fun setTheme(isDarkMode: Boolean) {
        val sharedPref = activity?.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.putBoolean("DARK_THEME", isDarkMode)
            this?.apply()
        }

        // Apply theme change immediately
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
