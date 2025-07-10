package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Success -> {
                    binding.usernamelayout.setText(it.data?.data?.talent?.username)
                    binding.emaillayout.setText(it.data?.data?.talent?.email)
                }
                is BaseResponse.Error -> {
                    binding.usernamelayout.setText("")
                    binding.emaillayout.setText("")
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.usernamelayout.setText("")
                    binding.emaillayout.setText("")
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}