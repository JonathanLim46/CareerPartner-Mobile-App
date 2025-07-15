package com.example.careerpartner.finishup.screen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.model.UserEducationRequest
import com.example.careerpartner.databinding.FragmentFinishUpEducationBinding
import com.example.careerpartner.finishup.adapter.EducationAdapter
import com.example.careerpartner.finishup.data.EducationData
import com.example.careerpartner.finishup.viewmodel.FinishUpViewModel
import java.util.Calendar

class FinishUpEducationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EducationAdapter
    private val educations = mutableListOf<EducationData>()

    private var _binding: FragmentFinishUpEducationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FinishUpViewModel by activityViewModels<FinishUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFinishUpEducationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvEdu
        adapter = EducationAdapter(educations)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        binding.btnContinue.setOnClickListener {
            educations.forEachIndexed { index, data ->
                viewModel.updateEducation(UserEducationRequest(
                    institutionName = data.institution,
                    fieldOfStudy = data.fieldOfStudy,
                    startYear = data.year.split("-")[0],
                    endYear = data.year.split("-")[1]
                ))
            }
            findNavController().navigate(R.id.action_finishUpEducationFragment_to_finishUpInterestsFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnAddEdu.setOnClickListener {
            openDialog()
        }
    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    private fun openDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_education, null)
        val etFieldOfStudy = dialogView.findViewById<EditText>(R.id.etFieldOfStudy)
        val etInstitution = dialogView.findViewById<EditText>(R.id.etInstitution)
        val etStartYear = dialogView.findViewById<EditText>(R.id.etStartYear)
        val etEndYear = dialogView.findViewById<EditText>(R.id.etEndYear)
        val btnAdd = dialogView.findViewById<AppCompatButton>(R.id.btnAdd)
        val btnCancel = dialogView.findViewById<AppCompatButton>(R.id.btnCancel)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        etStartYear.setOnClickListener {
            openDateDialog(etStartYear)
        }

        etEndYear.setOnClickListener {
            openDateDialog(etEndYear)
        }

        btnAdd.setOnClickListener {
            val fieldOfStudy = etFieldOfStudy.text.toString()
            val institution = etInstitution.text.toString()
            val startYear = etStartYear.text.toString()
            val endYear = etEndYear.text.toString()

            if (fieldOfStudy.isNotBlank() && institution.isNotBlank() && startYear.isNotBlank() && endYear.isNotBlank()) {
                val educationData = EducationData(fieldOfStudy, institution, "$startYear - $endYear")
                educations.add(educationData)
                adapter.notifyItemInserted(educations.size - 1)
                recyclerView.scrollToPosition(educations.size - 1)
                dialog.dismiss()
            } else {
                Toast.makeText(requireActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    @SuppressLint("DiscouragedApi")
    private fun openDateDialog(editText: EditText){
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