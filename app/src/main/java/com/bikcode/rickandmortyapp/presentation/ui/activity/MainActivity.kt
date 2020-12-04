package com.bikcode.rickandmortyapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentController
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.presentation.ui.fragment.FavoriteFragment
import com.bikcode.rickandmortyapp.presentation.ui.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        setEvents()
    }

    private fun initComponents() {
        bottomNavigation = findViewById(R.id.main_bottom_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setEvents() {
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.menu_favorites -> {
                    navController.navigate(R.id.favoriteFragment)
                    true
                }
                else -> false
            }
        }
    }
}