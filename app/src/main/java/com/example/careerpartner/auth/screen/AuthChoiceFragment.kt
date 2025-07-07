package com.example.careerpartner.auth.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.data.network.SessionManager

class AuthChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_auth_choice, container, false)
        view.findViewById<AppCompatButton>(R.id.btnSignIn).setOnClickListener {
            val token = SessionManager.getToken(requireContext().applicationContext)
            Log.d("AuthChoice", "SignIn Clicked, token: $token")
            findNavController().navigate(
                if (!token.isNullOrEmpty()) R.id.my_nav_main else R.id.signInFragment
            )
        }
        view.findViewById<AppCompatButton>(R.id.btnSignUp).setOnClickListener {
            findNavController().navigate(R.id.action_authChoiceFragment_to_signUpFragment)
        }
        return view
    }
}