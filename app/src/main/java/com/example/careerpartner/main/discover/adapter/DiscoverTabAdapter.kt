package com.example.careerpartner.main.discover.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.careerpartner.main.discover.screen.DiscoverAllFragment
import com.example.careerpartner.main.discover.screen.DiscoverInternshipsFragment
import com.example.careerpartner.main.discover.screen.DiscoverVolunteersFragment

class DiscoverTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {DiscoverAllFragment()}
            1 -> {DiscoverInternshipsFragment()}
            2 -> {DiscoverVolunteersFragment()}
            else -> {
                DiscoverAllFragment()
            }
        }
    }

}