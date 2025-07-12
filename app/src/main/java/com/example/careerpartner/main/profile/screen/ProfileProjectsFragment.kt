package com.example.careerpartner.main.profile.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.careerpartner.databinding.DialogChooseBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileProjectsFragment : Fragment() {

    private var _binding: FragmentProfileProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileProjectAdapter
    private lateinit var projectsData: List<ProfileProjectsData>
    private lateinit var recyclerView: RecyclerView

    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

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
    }
}