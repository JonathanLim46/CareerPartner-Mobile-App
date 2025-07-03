package com.example.careerpartner.main.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.main.profile.data.ProfileProjectsData

class ProfileProjectAdapter(private val profileData: List<ProfileProjectsData>) : RecyclerView.Adapter<ProfileProjectAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleProject: TextView = itemView.findViewById(R.id.tvProjectsTitle)
        val year: TextView = itemView.findViewById(R.id.tvProjectsYear)
        val imageProject: ImageView = itemView.findViewById(R.id.ivProjects)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_card_profile_projects, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = profileData[position]
        holder.titleProject.text = item.title
        holder.year.text = item.year
        holder.imageProject.setImageResource(item.image)
    }


}