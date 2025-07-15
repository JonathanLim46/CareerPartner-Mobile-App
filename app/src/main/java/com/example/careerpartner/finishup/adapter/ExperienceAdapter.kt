package com.example.careerpartner.finishup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.finishup.data.ExperienceData

class ExperienceAdapter(private val experiences: List<ExperienceData>) : RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {
    class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvFieldStudy)
        val nomination: TextView = itemView.findViewById(R.id.tvNameLabel)
        val year: TextView = itemView.findViewById(R.id.tvStudyStartYear)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExperienceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_finish_up, parent, false)
        return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val item = experiences[position]
        holder.title.text = item.title
        holder.nomination.text = item.nomination
        holder.year.text = item.year
    }

    override fun getItemCount(): Int {
        return experiences.size
    }

}