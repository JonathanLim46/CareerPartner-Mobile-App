package com.example.careerpartner

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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



        val fromLogout = requireActivity().intent?.getBooleanExtra("from_logout", false) ?: false

        Handler(Looper.getMainLooper()).postDelayed({
            val token = SessionManager.getToken(requireContext().applicationContext)
            Log.d("SplashFragment", "Token saat Splash: $token")
            if (fromLogout) {
                findNavController().navigate(R.id.action_boardingFragment_to_authChoiceFragment)
            } else if (onBoardingFinished() && !token.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_boardingFragment_to_my_nav_main)
            } else if (onBoardingFinished()) {
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