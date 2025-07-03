package com.example.careerpartner.main.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.careerpartner.main.profile.screen.ProfileAchievementsFragment
import com.example.careerpartner.main.profile.screen.ProfileEducationalsFragment
import com.example.careerpartner.main.profile.screen.ProfileMyCareerFragment
import com.example.careerpartner.main.profile.screen.ProfileProjectsFragment

class ProfileTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ProfileMyCareerFragment()
            }

            1 -> {
                ProfileEducationalsFragment()
            }

            2 -> {
                ProfileAchievementsFragment()
            }

            3 -> {
                ProfileProjectsFragment()
            }

            else -> {
                ProfileMyCareerFragment()
            }
        }
    }
}