package com.example.careerpartner.main.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.careerpartner.R
import com.example.careerpartner.data.model.ProjectData
import com.example.careerpartner.main.profile.data.ProfileProjectsData
import kotlinx.coroutines.withContext

class ProfileProjectAdapter(
    private val context: Context,
    private val profileData: List<ProfileProjectsData>
) : RecyclerView.Adapter<ProfileProjectAdapter.ViewHolder>() {

    var onMoreClick: ((ProfileProjectsData) -> Unit)? = null

    var onSourceClick: ((ProfileProjectsData) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleProject: TextView = itemView.findViewById(R.id.tvProjectsTitle)
        val year: TextView = itemView.findViewById(R.id.tvProjectsYear)
        val imageProject: ImageView = itemView.findViewById(R.id.ivProjects)
        val more: Button = itemView.findViewById(R.id.btnMore)
        val openSource: Button = itemView.findViewById(R.id.btnOpenSource)

        init {
            more.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMoreClick?.invoke(profileData[position])
                }
            }
            openSource.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onSourceClick?.invoke(profileData[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_card_profile_projects, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profileData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = profileData[position]
        holder.titleProject.text = item.title
        holder.year.text = item.year
        Glide.with(context).load(item.image).placeholder(R.drawable.dummyimg)
            .error(R.drawable.dummyimg).skipMemoryCache(true).into(holder.imageProject)
    }


}