package com.example.careerpartner.main.profile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.main.profile.data.ProfileCareerData
import com.example.careerpartner.main.profile.data.ProfileHistoryData

class ProfileAdapter(private val profileData: List<ProfileCareerData>) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    var onItemClick: ((ProfileCareerData) -> Unit)? = null
    var onItemDone: ((ProfileCareerData) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvMyCareerPathCourseTitle)
        val source: TextView = itemView.findViewById(R.id.tvMyCareerPathCourseSource)
        val number: TextView = itemView.findViewById(R.id.tvMyCareerPathCourseNumber)
        val openSource: Button = itemView.findViewById(R.id.btnOpenSource)
        val btnDone: Button = itemView.findViewById(R.id.btnDone)

        init {
            openSource.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(profileData[position])
                }
            }

            btnDone.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemDone?.invoke(profileData[position])
                }
            }
        }
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