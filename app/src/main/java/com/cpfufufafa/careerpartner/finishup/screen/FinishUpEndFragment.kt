package com.cpfufufafa.careerpartner.finishup.screen

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cpfufufafa.careerpartner.R
import com.cpfufufafa.careerpartner.data.viewmodel.UserViewModel
import com.cpfufufafa.careerpartner.databinding.DialogConfirmationFinishupBinding
import com.cpfufufafa.careerpartner.databinding.FragmentFinishUpEndBinding
import com.cpfufufafa.careerpartner.finishup.viewmodel.FinishUpViewModel

class FinishUpEndFragment : Fragment() {

    private var _binding: FragmentFinishUpEndBinding? = null
    private val binding: FragmentFinishUpEndBinding get() = _binding!!

    private lateinit var loadingDialog: LoadingDialog

    private val viewModel: FinishUpViewModel by activityViewModels<FinishUpViewModel>()
    private val viewModelUser: UserViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFinishUpEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()
        binding.btnContinue.setOnClickListener {
            openDialog()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                loadingDialog.startLoadingDialog(requireActivity())
            } else {
                loadingDialog.dismissDialog()
            }
        }

        viewModel.submitCompleted.observe(viewLifecycleOwner) { completed ->
            if (completed) {
                viewModelUser.generateProfileCareer(requireActivity())
                findNavController().navigate(R.id.action_finishUpEndFragment_to_my_nav_main)
                viewModel.resetSubmitCompleted()
            }
        }
    }

    private fun openDialog(){
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_confirmation_finishup, null)
        val bindingDialog = DialogConfirmationFinishupBinding.bind(dialogView)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        dialog.show()

        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        bindingDialog.btnAdd.setOnClickListener {
            dialog.dismiss()
            viewModel.submitAllData(requireActivity())
        }
    }
}