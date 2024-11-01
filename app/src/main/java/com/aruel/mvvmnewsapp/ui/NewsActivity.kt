package com.aruel.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aruel.mvvmnewsapp.R
import com.aruel.mvvmnewsapp.db.EventDatabase
import com.aruel.mvvmnewsapp.repository.EventRepository
import com.aruel.mvvmnewsapp.util.ThemeUtil
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtil.applyTheme(this)  // Apply theme on start
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val eventRepository = EventRepository(EventDatabase(this))
        val viewModelProviderFactory = EventViewModelProviderFactory(eventRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(EventViewModel::class.java)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        bottomNavigationView.setupWithNavController(navController)
    }
}
