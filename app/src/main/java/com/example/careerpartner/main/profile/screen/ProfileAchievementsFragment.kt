package com.example.careerpartner.main.profile.screen

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.model.UserAchievementsRequest
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.DialogAddAchievementBinding
import com.example.careerpartner.databinding.DialogChooseBottomBinding
import com.example.careerpartner.databinding.FragmentProfileAchievementsBinding
import com.example.careerpartner.main.profile.adapter.ProfileEduAchieveAdapter
import com.example.careerpartner.main.profile.data.ProfileHistoryData
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar

class ProfileAchievementsFragment : Fragment() {

    private var _binding: FragmentProfileAchievementsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileEduAchieveAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var educationData: List<ProfileHistoryData>

    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvAchievement
        viewModel.getAchievementsData(requireActivity())

        binding.tvAdd.setOnClickListener {
            openDialog(null)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAchievementsData(requireActivity())
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.userAchievementResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.rvAchievement.visibility = View.GONE
                    binding.shimmerProfileAch.visibility = View.VISIBLE
                    binding.shimmerProfileAch.startShimmer()
                }
                is BaseResponse.Success -> {
                    educationData = it.data?.data?.achievements?.map {
                        ProfileHistoryData(
                            id = it.id,
                            title = it.title,
                            source = it.nomination,
                            year = it.year
                        )
                    } ?: listOf()
                    setupRv()
                    binding.rvAchievement.visibility = View.VISIBLE
                    binding.shimmerProfileAch.stopShimmer()
                    binding.shimmerProfileAch.visibility = View.GONE
                }

                is BaseResponse.Error -> {
                    educationData = listOf()
                    setupRv()
                }

                else -> {
                    educationData = listOf()
                    setupRv()
                }
            }
        }

        viewModel.userAddAchievementResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        viewModel.getAchievementsData(requireActivity())
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

        viewModel.userDeleteAchievementResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        viewModel.getAchievementsData(requireActivity())
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

        viewModel.userUpdateAchievementResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        viewModel.getAchievementsData(requireActivity())
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
            openBottomDialog(item)
        }
    }

    private fun openBottomDialog(item: ProfileHistoryData?) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_choose_bottom, null)
        val dialogBinding = DialogChooseBottomBinding.bind(dialogView)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogView)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.tvEditDialog.setOnClickListener {
            openDialog(item)
            dialog.dismiss()
        }

        dialogBinding.tvDelete.setOnClickListener {
            viewModel.deleteAchievementData(requireActivity(), item?.id ?: 0)
            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun openDialog(item: ProfileHistoryData?) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_achievement, null)
        val dialogBinding = DialogAddAchievementBinding.bind(dialogView)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.etYear.setOnClickListener {
            openDateDialog(dialogBinding.etYear)
        }

        item?.let {
            dialogBinding.etAchievement.setText(it.title)
            dialogBinding.etNomination.setText(it.source)
            dialogBinding.etYear.setText(it.year)
        }

        dialogBinding.btnAdd.setOnClickListener {
            val title = dialogBinding.etAchievement.text.toString()
            val source = dialogBinding.etNomination.text.toString()
            val year = dialogBinding.etYear.text.toString()

            if (title.isNotEmpty() && source.isNotEmpty() && year.isNotEmpty()) {
                if (item != null) {
                    viewModel.updateAchievementData(
                        requireActivity(), item.id,
                        UserAchievementsRequest(title, source, year)
                    )
                } else {
                    viewModel.addAchievementData(requireActivity(), title, source, year)
                }
                dialog.dismiss()
            } else {
                Toast.makeText(requireActivity(), "Please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
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

        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
    }
}