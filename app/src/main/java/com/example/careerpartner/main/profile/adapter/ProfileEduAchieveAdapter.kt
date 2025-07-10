package com.example.careerpartner.main.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.main.profile.data.ProfileHistoryData

class ProfileEduAchieveAdapter(private val profileData: List<ProfileHistoryData>) : RecyclerView.Adapter<ProfileEduAchieveAdapter.ViewHolder>() {

    var onItemClick: ((ProfileHistoryData) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val subtitle: TextView = itemView.findViewById(R.id.tvSubtitle)
        val years: TextView = itemView.findViewById(R.id.tvYears)
        val more: Button = itemView.findViewById(R.id.more)

        init {
            more.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(profileData[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_card_profile_edu_achiev, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = profileData[position]
        holder.title.text = item.title
        holder.subtitle.text = item.source
        holder.years.text = item.year
    }
}