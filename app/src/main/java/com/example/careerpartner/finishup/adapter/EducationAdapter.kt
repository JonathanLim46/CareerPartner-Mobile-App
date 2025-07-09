package com.example.careerpartner.finishup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.finishup.data.EducationData

class EducationAdapter(private val educations: List<EducationData>): RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EducationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_finish_up_education, parent, false)
        return EducationViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EducationViewHolder,
        position: Int
    ) {
        val item = educations[position]
        holder.fieldOfStudyTextView.text = item.fieldOfStudy
        holder.institutionTextView.text = item.institution
        holder.yearTextView.text = item.year
    }

    override fun getItemCount(): Int {
        return educations.size
    }

    inner class EducationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fieldOfStudyTextView: TextView = itemView.findViewById(R.id.tvFieldStudy)
        val institutionTextView: TextView = itemView.findViewById(R.id.tvNameLabel)
        val yearTextView: TextView = itemView.findViewById(R.id.tvStudyStartYear)
    }
}