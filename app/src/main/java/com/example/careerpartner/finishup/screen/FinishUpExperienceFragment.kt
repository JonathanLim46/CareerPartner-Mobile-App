package com.example.careerpartner.finishup.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.model.UserAchievementsRequest
import com.example.careerpartner.finishup.adapter.ExperienceAdapter
import com.example.careerpartner.finishup.data.ExperienceData
import com.example.careerpartner.finishup.viewmodel.FinishUpViewModel
import java.util.Calendar

class FinishUpExperienceFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExperienceAdapter
    private val experiences = mutableListOf<ExperienceData>()

    private val viewModel: FinishUpViewModel by activityViewModels<FinishUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_finish_up_experience, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.rvExp)
        adapter = ExperienceAdapter(experiences)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.btnAddExp).setOnClickListener {
            openDialog()
        }

        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<Button>(R.id.btnContinue).setOnClickListener {
            experiences.forEachIndexed { index, data ->
                viewModel.updateAchievement(
                    UserAchievementsRequest(
                        title = data.title,
                        nominationData = data.nomination,
                        year = data.year
                    )
                )
            }
            findNavController().navigate(R.id.action_finishUpExperienceFragment_to_finishUpEndFragment)
        }


    }

    @SuppressLint("InflateParams")
    private fun openDialog(){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_achievement, null)
        val editAchievement = dialogView.findViewById<EditText>(R.id.etAchievement)
        val editNomination = dialogView.findViewById<EditText>(R.id.etNomination)
        val editYear = dialogView.findViewById<EditText>(R.id.etYear)
        val btnAdd = dialogView.findViewById<Button>(R.id.btnAdd)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        editYear.setOnClickListener {
            openDateDialog(editYear)
        }

        btnAdd.setOnClickListener {
            val inputAch = editAchievement.text.toString()
            val inputNomination = editNomination.text.toString()
            val inputYear = editYear.text.toString()
            if (inputAch.isNotBlank() && inputNomination.isNotBlank() && inputYear.isNotBlank()){
                experiences.add(ExperienceData(inputAch, inputNomination, inputYear))
                adapter.notifyItemInserted(experiences.size - 1)
                recyclerView.scrollToPosition(experiences.size - 1)
                dialog.dismiss()
            } else {
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

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