package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.careerpartner.R
import com.example.careerpartner.databinding.FragmentProfileBinding
import com.example.careerpartner.main.profile.adapter.ProfileTabAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding : FragmentProfileBinding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: ProfileTabAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = binding.viewPagerProfile
        adapter = ProfileTabAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        val tabTitles = listOf("My Career", "Educational", "Achievements", "Projects")

        TabLayoutMediator(binding.tabLayoutProfile, viewPager2) { tab, position ->
            val tabView = LayoutInflater.from(binding.root.context).inflate(R.layout.item_custom_tab_item, null)
            tabView.findViewById<TextView>(R.id.tabText).text = tabTitles[position]
            tab.customView = tabView
        }.attach()

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }
    }
}