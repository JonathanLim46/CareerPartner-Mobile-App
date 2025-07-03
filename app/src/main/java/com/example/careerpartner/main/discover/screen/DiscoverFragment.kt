package com.example.careerpartner.main.discover.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.careerpartner.databinding.FragmentDiscoverBinding
import com.example.careerpartner.main.discover.adapter.DiscoverTabAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DiscoverFragment : Fragment() {

    private var _binding : FragmentDiscoverBinding? = null
    private val binding: FragmentDiscoverBinding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: DiscoverTabAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = binding.viewPagerDiscover
        adapter = DiscoverTabAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayoutDiscover, viewPager2) { tab, position ->
            when (position) {
                0 -> {tab.text = "All"}
                1 -> {tab.text = "Internships"}
                2 -> {tab.text = "Volunteers"}
            }
        }.attach()
    }

}