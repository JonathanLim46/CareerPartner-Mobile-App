package com.example.careerpartner.main.profile.screen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentProfileEducationalsBinding
import com.example.careerpartner.main.profile.adapter.ProfileEduAchieveAdapter
import com.example.careerpartner.main.profile.data.ProfileHistoryData
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar

class ProfileEducationalsFragment : Fragment() {

    private var _binding: FragmentProfileEducationalsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    private lateinit var adapter: ProfileEduAchieveAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var educationData: List<ProfileHistoryData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileEducationalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvEducation


        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getEducationData(requireActivity())
        }

        binding.tvAdd.setOnClickListener {
            openEducationDialog(null)
        }

        viewModel.getEducationData(requireActivity())

        viewModel.userEducationResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    educationData = it.data?.data?.map {
                        ProfileHistoryData(
                            it.id,
                            it.institutionName,
                            it.fieldOfStudy,
                            it.startDate + " - " + it.endDate
                        )
                    } ?: listOf()
                    setupRv()
                }

                is BaseResponse.Error -> {
                    educationData = listOf()
                    setupRv()
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    educationData = listOf()
                    setupRv()
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.userUpdateEducationResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(
                            requireActivity(),
                            it.data?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.getEducationData(requireActivity())
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

        viewModel.userDeleteEducationResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(
                            requireActivity(),
                            it.data?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.getEducationData(requireActivity())
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

        viewModel.userAddEducationResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(
                            requireActivity(),
                            it.data?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.getEducationData(requireActivity())
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
        adapter = ProfileEduAchieveAdapter(educationData)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.onItemClick = { item ->
            openBottomDialog(item.id)
        }
    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    private fun openBottomDialog(id: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_choose_bottom, null)

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        view.findViewById<TextView>(R.id.tvEditDialog).setOnClickListener {
            openEducationDialog(id)
            dialog.dismiss()
        }

        view.findViewById<TextView>(R.id.tvDelete).setOnClickListener {
            viewModel.deleteUserEducationData(requireActivity(), id)
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun openEducationDialog(id: Int?) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_education, null)
        val etFieldOfStudy = dialogView.findViewById<EditText>(R.id.etFieldOfStudy)
        val etInstitution = dialogView.findViewById<EditText>(R.id.etInstitution)
        val etStartYear = dialogView.findViewById<EditText>(R.id.etStartYear)
        val etEndYear = dialogView.findViewById<EditText>(R.id.etEndYear)
        val btnAdd = dialogView.findViewById<AppCompatButton>(R.id.btnAdd)
        val btnCancel = dialogView.findViewById<AppCompatButton>(R.id.btnCancel)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        etStartYear.setOnClickListener {
            openDateDialog(etStartYear)
        }

        etEndYear.setOnClickListener {
            openDateDialog(etEndYear)
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnAdd.setOnClickListener {
            val fieldOfStudy = etFieldOfStudy.text.toString()
            val institution = etInstitution.text.toString()
            val startYear = etStartYear.text.toString()
            val endYear = etEndYear.text.toString()
            if (fieldOfStudy.isNotEmpty() && institution
                    .isNotEmpty() && startYear
                    .isNotEmpty() && endYear.isNotEmpty()
            ) {
                if (id != null) {
                    viewModel.updateUserEducationData(
                        requireActivity(),
                        id = id,
                        institutionName = institution,
                        fieldOfStudy = fieldOfStudy,
                        startYear = startYear,
                        endYear = endYear
                    )
                    dialog.dismiss()
                } else {
                    viewModel.addUserData(
                        requireActivity(),
                        institutionName = institution,
                        fieldOfStudy = fieldOfStudy,
                        startYear = startYear,
                        endYear = endYear
                    )
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialog.show()
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