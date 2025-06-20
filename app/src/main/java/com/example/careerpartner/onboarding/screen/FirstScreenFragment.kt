package com.example.careerpartner.onboarding.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.careerpartner.R

class FirstScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.findViewById<AppCompatButton>(R.id.btnBoardingOne).setOnClickListener({
            viewPager?.currentItem = 1
        })

        view.findViewById<TextView>(R.id.tvSkip).setOnClickListener({
            findNavController().navigate(R.id.action_viewPagerFragment_to_authChoiceFragment)
            onBoardingFinished()
        })

        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        sharedPref.edit {
            putBoolean("Finished", true)
        }
    }
}