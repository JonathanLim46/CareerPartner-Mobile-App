package com.example.careerpartner.main.profile.screen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.MainActivity
import com.example.careerpartner.R
import com.example.careerpartner.auth.viewmodel.AuthViewModel
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewmodel : AuthViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.logoutResult.observe(requireActivity()) {
            context?.let { safeContext ->
                when  (it){
                    is BaseResponse.Success -> {
                        SessionManager.clearData(safeContext.applicationContext)

                        val intent = Intent(safeContext, MainActivity::class.java)
                        intent.putExtra("from_logout", true)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    is BaseResponse.Error -> {
                        Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    } else -> {
                    Toast.makeText(requireActivity(), "Logout Failed", Toast.LENGTH_SHORT).show()
                }
                }
            }
        }

        binding.btnAbout.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }

        binding.btnSupportHelp.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_supportHelpFragment)
        }

        binding.btnGradientButton.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnLogout.setOnClickListener {
            doLogout()
        }

        binding.btnResetSuggestions.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_resetAccountPreferenceFragment)
        }
    }

    private fun doLogout(){
        viewmodel.logout(requireActivity())
    }

}