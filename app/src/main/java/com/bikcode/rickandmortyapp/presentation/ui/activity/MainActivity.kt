package com.bikcode.rickandmortyapp.presentation.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bikcode.rickandmortyapp.R
import com.bikcode.rickandmortyapp.databinding.ActivityMainBinding
import com.bikcode.rickandmortyapp.presentation.ui.adapter.NavigationStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val homeStatePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            main_vp_container.currentItem = position
            when (position) {
                0 -> binding.mainBottomNavigation.menu.findItem(R.id.menu_home).isChecked = true
                1 -> binding.mainBottomNavigation.menu.findItem(R.id.menu_favorites).isChecked =
                    true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initComponents()
    }

    private fun initComponents() {
        binding.mainBottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.mainVpContainer.run {
            adapter = NavigationStateAdapter(this@MainActivity)
            registerOnPageChangeCallback(homeStatePageChangeCallback)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home -> {
                main_vp_container.currentItem = 0
                true
            }
            R.id.menu_favorites -> {
                main_vp_container.currentItem = 1
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mainVpContainer.unregisterOnPageChangeCallback(homeStatePageChangeCallback)
    }
}