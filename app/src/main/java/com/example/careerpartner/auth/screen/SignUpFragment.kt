package com.example.careerpartner.auth.screen

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.auth.model.LoginResponse
import com.example.careerpartner.auth.model.RegisterRequest
import com.example.careerpartner.auth.viewmodel.AuthViewModel
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.databinding.DialogConfirmationSignupBinding
import com.example.careerpartner.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {


    private lateinit var fullName: String
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var phoneNumber: String

    private lateinit var loading: LoadingDialog

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModelAuth: AuthViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading = LoadingDialog()
        binding.ivLogoBack1.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSignUp.setOnClickListener {
            validateData()
        }

        viewModelAuth.loginResult.observe(viewLifecycleOwner) {
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

        viewModelAuth.registerResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    viewModelAuth.login(email, password)
                }
                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateData() {
        fullName = binding.nameInput.text.toString().trim()
        username = binding.usernameInput.text.toString().trim()
        email = binding.emailInput.text.toString().trim()
        password = binding.passwordInput.text.toString().trim()
        confirmPassword = binding.confirmPasswordInput.text.toString().trim()
        phoneNumber = binding.phoneInput.text.toString().trim()

        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(requireActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Email is invalid"
            Toast.makeText(requireActivity(), "Email is invalid", Toast.LENGTH_SHORT).show()
        } else if (password != confirmPassword) {
            binding.confirmPassword.error = "Password is not the same"
            Toast.makeText(requireActivity(), "Password is not the same", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            binding.phone.error = "Phone number is invalid"
            Toast.makeText(requireActivity(), "Phone number is invalid", Toast.LENGTH_SHORT).show()
        } else if(!binding.checkboxAgree.isChecked){
            Toast.makeText(requireActivity(), "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show()
        } else {
            dialogConfirmation(
                fullName = fullName,
                username = username,
                email = email,
                password = password,
                phoneNumber = phoneNumber
            )
        }
    }

    private fun dialogConfirmation(
        fullName: String,
        username: String,
        email: String,
        password: String,
        phoneNumber: String
    ) {
        val dialogView = LayoutInflater.from(requireActivity())
            .inflate(R.layout.dialog_confirmation_signup, null)
        val bindingDialog = DialogConfirmationSignupBinding.bind(dialogView)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .create()
        dialog.show()

        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialog.ivClose.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialog.btnAdd.setOnClickListener {
            viewModelAuth.register(RegisterRequest(
                fullName = fullName,
                username = username,
                email = email,
                password = password,
                phone = phoneNumber,
                role = "talent"
            ))
            dialog.dismiss()
        }
    }

    private fun processLogin(data: LoginResponse?){
        if (data?.data?.user?.role == "talent"){
            Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
            if (!data?.data?.token.isNullOrBlank()){
                data?.data?.token?.let {
                    SessionManager.saveAuthToken(requireActivity(), it)
                }
                findNavController().navigate(R.id.action_signUpFragment_to_finishUpEducationFragment)
            }
        } else {
            Toast.makeText(requireActivity(), "You are not a talent and go use a Website", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(){
        loading.startLoadingDialog(requireActivity())
    }

    private fun stopLoading(){
        loading.dismissDialog()
    }

    private fun processError(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }
}