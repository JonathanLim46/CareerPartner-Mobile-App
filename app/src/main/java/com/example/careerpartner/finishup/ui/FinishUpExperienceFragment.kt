package com.example.careerpartner.finishup.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.finishup.adapter.ExperienceAdapter

class FinishUpExperienceFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExperienceAdapter
    private val experiences = mutableListOf<String>()

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
            findNavController().navigate(R.id.action_finishUpExperienceFragment_to_finishUpEndFragment)
        }


    }

    @SuppressLint("InflateParams")
    private fun openDialog(){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_experience, null)
        val editText = dialogView.findViewById<EditText>(R.id.etExperience)
        val btnAdd = dialogView.findViewById<Button>(R.id.btnAdd)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        btnAdd.setOnClickListener {
            val input = editText.text.toString()
            if (input.isNotBlank()){
                experiences.add(input)
                adapter.notifyItemInserted(experiences.size - 1)
                recyclerView.scrollToPosition(experiences.size - 1)
                dialog.dismiss()
            } else {
                editText.error = "Please enter an experience"
            }
        }

        dialog.show()
    }
}