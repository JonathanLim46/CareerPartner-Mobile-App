package com.example.careerpartner.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R

class AuthChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_auth_choice, container, false)
        view.findViewById<AppCompatButton>(R.id.btnSignIn).setOnClickListener {
            findNavController().navigate(R.id.action_authChoiceFragment_to_signInFragment)
        }
        view.findViewById<AppCompatButton>(R.id.btnSignUp).setOnClickListener {
            findNavController().navigate(R.id.action_authChoiceFragment_to_signUpFragment)
        }
        return view
    }
}