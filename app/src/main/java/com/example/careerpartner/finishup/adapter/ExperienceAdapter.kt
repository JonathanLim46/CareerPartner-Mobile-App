package com.example.careerpartner.finishup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R

class ExperienceAdapter(private val experiences: List<String>) : RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {
    class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tvExpAdded)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExperienceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_finish_up_experience, parent, false)
        return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val item = experiences[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return experiences.size
    }

}