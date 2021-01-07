package com.bikcode.rickandmortyapp.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bikcode.rickandmortyapp.presentation.ui.fragment.FavoriteFragment
import com.bikcode.rickandmortyapp.presentation.ui.fragment.HomeFragment

class NavigationStateAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private val homeStateFragmentList: List<Fragment> = listOf(
        HomeFragment.newInstance(),
        FavoriteFragment.newInstance()
    )

    override fun getItemCount(): Int = homeStateFragmentList.size

    override fun createFragment(position: Int): Fragment = homeStateFragmentList[position]
}