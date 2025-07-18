package com.example.careerpartner.onboarding.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import androidx.core.content.edit

class ThirdScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)
        view.findViewById<AppCompatButton>(R.id.btnBoardingThree).setOnClickListener({
            findNavController().navigate(R.id.action_viewPagerFragment_to_authChoiceFragment)
            onBoardingFinished()
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