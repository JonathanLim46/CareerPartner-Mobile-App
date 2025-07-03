package com.example.careerpartner.main.profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.main.profile.data.ProfileCareerData

class ProfileAdapter(private val profileData: List<ProfileCareerData>) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvMyCareerPathCourseTitle)
        val source: TextView = itemView.findViewById(R.id.tvMyCareerPathCourseSource)
        val number: TextView = itemView.findViewById(R.id.tvMyCareerPathCourseNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_card_profile_path, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = profileData[position]
        holder.title.text = item.title
        holder.source.text = item.source
        holder.number.text = (position + 1).toString()
    }

    override fun getItemCount(): Int {
        return profileData.size
    }
}