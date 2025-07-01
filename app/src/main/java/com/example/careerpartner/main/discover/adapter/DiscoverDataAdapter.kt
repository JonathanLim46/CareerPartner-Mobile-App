package com.example.careerpartner.main.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.main.discover.data.DiscoverData

class DiscoverDataAdapter(private val discoverData : List<DiscoverData>) : RecyclerView.Adapter<DiscoverDataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitleCardDiscover)
        val subTitle: TextView = itemView.findViewById(R.id.tvSubTitleCardDiscover)
        val contentCard: TextView = itemView.findViewById(R.id.tvContentCardDiscover)
        val imageCard: ImageView = itemView.findViewById(R.id.ivCardDiscover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_card_discover, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return discoverData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = discoverData[position]
        holder.title.text = item.title
        holder.subTitle.text = item.subTitle
        holder.contentCard.text = item.content
        holder.imageCard.setImageResource(item.image)
    }

}