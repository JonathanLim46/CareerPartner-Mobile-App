package com.example.careerpartner.auth.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.auth.model.LoginResponse
import com.example.careerpartner.auth.viewmodel.AuthViewModel
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingDialog: LoadingDialog

    private val viewModel : AuthViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()

        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }
                is BaseResponse.Success -> {
                    stopLoading()
                    processLogin(it.data)
                }
                is BaseResponse.Error -> {
                    stopLoading()
                    processError(it.msg.toString())
                }
                else -> {
                    stopLoading()
                }
            }
        }

        binding.btnSignIn.setOnClickListener {
            doLogin()
        }

        binding.ivLogoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun doLogin(){
        val identifier = binding.etIdentifier.text.toString()
        val password = binding.etPassword.text.toString()
        if (identifier.isEmpty() || password.isEmpty()){
            Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.login(identifier, password)
    }

    private fun processLogin(data: LoginResponse?){
        if (data?.data?.user?.role == "talent"){
            Toast.makeText(requireActivity(), "Login Success", Toast.LENGTH_SHORT).show()
            if (!data?.data?.token.isNullOrBlank()){
                data?.data?.token?.let {
                    SessionManager.saveAuthToken(requireActivity(), it)
                }
                findNavController().navigate(R.id.action_signInFragment_to_my_nav_main)
            }
        } else {
            Toast.makeText(requireActivity(), "You are not a talent and go use a Website", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(){
        loadingDialog.startLoadingDialog(requireActivity())
    }

    private fun stopLoading(){
        loadingDialog.dismissDialog()
    }

    private fun processError(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }
}