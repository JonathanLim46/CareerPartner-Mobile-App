package com.example.careerpartner.main.profile.screen

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.model.ProjectData
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentProfileProjectsBinding
import com.example.careerpartner.main.profile.adapter.ProfileProjectAdapter
import com.example.careerpartner.main.profile.data.ProfileProjectsData
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.careerpartner.databinding.DialogAddProjectsBinding
import com.example.careerpartner.databinding.DialogChooseBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.util.Calendar

class ProfileProjectsFragment : Fragment() {

    private var _binding: FragmentProfileProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileProjectAdapter
    private lateinit var projectsData: List<ProfileProjectsData>
    private lateinit var recyclerView: RecyclerView
    private var selectedImageUri: Uri? = null
    private var dialogProjectBinding: DialogAddProjectsBinding? = null
    private var selectedImageFile: File? = null

    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    var contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            selectedImageUri = it
            selectedImageFile = uriToFile(it)
            dialogProjectBinding?.ivProject?.setImageURI(it)
        } else {
            Toast.makeText(requireActivity(), "No Image Selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvProjects

        binding.tvAdd.setOnClickListener {
            openDialog()
        }


        viewModel.getProjectsData(requireActivity())

        viewModel.userProjectsResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    projectsData = it.data?.data?.map {
                        ProfileProjectsData(
                            id = it.id,
                            title = it.title,
                            year = it.year,
                            image = it.image,
                            link = it.link
                        )
                    } ?: listOf()
                    setupRv()
                    adapter.onSourceClick = { item ->
                        var url = item.link.orEmpty()
                        if (!url.startsWith("http://") && !url.startsWith("https://")) {
                            url = "http://$url"
                        }
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        startActivity(intent)
                    }
                }

                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    projectsData = listOf()
                    setupRv()
                }

                else -> {
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    projectsData = listOf()
                    setupRv()
                }
            }
        }

        viewModel.userDeleteProjectResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        viewModel.getProjectsData(requireActivity())
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

        viewModel.userAddProjectResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        viewModel.getProjectsData(requireActivity())
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

        viewModel.userUpdateProjectResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        viewModel.getProjectsData(requireActivity())
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

    private fun setupRv() {
        adapter = ProfileProjectAdapter(requireContext(), projectsData)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.onMoreClick = { item ->
            openBottomDialog(item)
        }
    }

    private fun openBottomDialog(item: ProfileProjectsData) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_choose_bottom, null)
        val dialogBinding = DialogChooseBottomBinding.bind(dialogView)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogView)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.tvDelete.setOnClickListener {
            viewModel.deleteProjectData(requireActivity(), item.id)
            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.tvEditDialog.setOnClickListener {
            openDialog(item)
            dialog.dismiss()
        }
    }

    private fun openDialog(item: ProfileProjectsData? = null) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_projects, null)
        dialogProjectBinding = DialogAddProjectsBinding.bind(dialogView)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        dialog.show()


        selectedImageUri?.let {
            dialogProjectBinding?.ivProject?.setImageURI(selectedImageUri)
        }

        dialogProjectBinding?.ivProject?.setOnClickListener {
            contract.launch("image/*")
        }

        dialogProjectBinding?.btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialogProjectBinding?.etYear?.setOnClickListener {
            openDateDialog(dialogProjectBinding?.etYear ?: EditText(requireContext()))
        }

        item?.let {
            dialogProjectBinding?.etTitle?.setText(it.title)
            dialogProjectBinding?.etLink?.setText(it.link)
            dialogProjectBinding?.etYear?.setText(it.year)
            Glide.with(requireContext()).load(it.image).into(dialogProjectBinding!!.ivProject)
        }

        dialogProjectBinding?.btnAdd?.setOnClickListener {
            val title = dialogProjectBinding!!.etTitle.text.toString()
            val link = dialogProjectBinding!!.etLink.text.toString()
            val year = dialogProjectBinding!!.etYear.text.toString()

            if (title.isNotEmpty() && link.isNotEmpty() && year.isNotEmpty()) {
                if (item != null) {
                    viewModel.updateProjectData(
                        requireActivity(),
                        item.id,
                        title,
                        selectedImageFile,
                        link,
                        year
                    )
                } else {
                    viewModel.addProjectData(
                        requireActivity(),
                        title,
                        selectedImageFile,
                        link,
                        year
                    )
                }

                dialog.dismiss()
            }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)!!
        val tempFile = File.createTempFile("upload_pp_", ".jpg", requireActivity().cacheDir)
        tempFile.outputStream().use {
            inputStream.copyTo(it)
        }
        return tempFile
    }

    private fun openDateDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, _, _ ->
                editText.setText(selectedYear.toString())
            },
            year,
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.findViewById<View>(
            resources.getIdentifier("day", "id", "android")
        )?.visibility = View.GONE

        datePickerDialog.datePicker.findViewById<View>(
            resources.getIdentifier("month", "id", "android")
        )?.visibility = View.GONE

        datePickerDialog.show()
    }
}