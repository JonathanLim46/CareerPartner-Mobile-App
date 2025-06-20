package com.example.careerpartner.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.careerpartner.R
import com.example.careerpartner.onboarding.screen.FirstScreenFragment
import com.example.careerpartner.onboarding.screen.SecondScreenFragment
import com.example.careerpartner.onboarding.screen.ThirdScreenFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ViewPagerFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val wormDotsIndicator = view.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter = adapter
        wormDotsIndicator.attachTo(viewPager)
        return view
    }
}