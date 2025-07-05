package com.example.careerpartner

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.data.network.SessionManager

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val token = SessionManager.getToken(requireActivity())

        Handler(Looper.getMainLooper()).postDelayed({
            if(onBoardingFinished() && token != null){
                findNavController().navigate(R.id.action_boardingFragment_to_my_nav_main)
            } else if (onBoardingFinished() && token == null){
                findNavController().navigate(R.id.action_boardingFragment_to_authChoiceFragment)
            } else {
                findNavController().navigate(R.id.action_boardingFragment_to_viewPagerFragment)
            }
        }, 3000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}