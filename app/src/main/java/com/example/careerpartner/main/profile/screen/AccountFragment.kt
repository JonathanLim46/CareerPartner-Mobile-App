package com.example.careerpartner.main.profile.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentAccountBinding
import java.io.File

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    private var selectedImageFile: File? = null

    var contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            selectedImageFile = uriToFile(it)
            binding.ivLogoFufufa.setImageURI(it)
        } else {
            Toast.makeText(requireActivity(), "No Image Selected", Toast.LENGTH_SHORT).show()
        }
    }

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
            when (it) {
                is BaseResponse.Success -> {
                    binding.usernamelayout.setText(it.data?.data?.talent?.username)
                    binding.emaillayout.setText(it.data?.data?.talent?.email)
                    Glide.with(requireActivity()).load(it.data?.data?.talent?.profile_picture).into(binding.ivLogoFufufa)
                }

                is BaseResponse.Error -> {
                    binding.usernamelayout.setText("")
                    binding.emaillayout.setText("")
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    binding.usernamelayout.setText("")
                    binding.emaillayout.setText("")
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.ivChangesImage.setOnClickListener {
            contract.launch("image/*")
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            viewModel.updateUserData(
                requireActivity(),
                selectedImageFile,
                binding.usernamelayout.text.toString(),
                binding.emaillayout.text.toString(),
                null,
                binding.passwordlayout.text.toString()
            )
        }

        viewModel.userUpdateResult.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()

                    }

                    is BaseResponse.Error -> {
                        Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(
                            requireActivity(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }

    }

    @SuppressLint("Recycle")
    private fun uriToFile(uri: Uri): File {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)!!
        val tempFile = File.createTempFile("upload_pp_", ".jpg", requireActivity().cacheDir)
        tempFile.outputStream().use {
            inputStream.copyTo(it)
        }
        return tempFile
    }
}
