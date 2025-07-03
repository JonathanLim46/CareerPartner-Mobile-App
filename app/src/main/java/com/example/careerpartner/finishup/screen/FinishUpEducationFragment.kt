package com.example.careerpartner.finishup.screen

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R

class FinishUpEducationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_finish_up_education, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()

        view.findViewById<AppCompatButton>(R.id.btnContinue).setOnClickListener {
            findNavController().navigate(R.id.action_finishUpEducationFragment_to_finishUpInterestsFragment)
        }

        view.findViewById<AppCompatButton>(R.id.btnBack).setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupSpinner(){
        val options = arrayOf("Select", "SMA", "S1", "S2")
        val spinner = view?.findViewById<Spinner>(R.id.spinnerEducation)
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            options
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val viewDropDown = super.getDropDownView(position, convertView, parent)
                val tv = viewDropDown as TextView
                if (position == 0) {
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return viewDropDown
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    Toast.makeText(requireContext(), "Please select an option", Toast.LENGTH_SHORT).show()
                } else {
                    val selectedEducation = options[position]
                    Toast.makeText(requireContext(), "You choose $selectedEducation", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}