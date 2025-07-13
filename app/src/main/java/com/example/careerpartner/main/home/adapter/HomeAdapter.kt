package com.example.careerpartner.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.careerpartner.R
import com.example.careerpartner.main.home.data.HomeData

class HomeAdapter(private val context: Context, private val homeData: List<HomeData>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitleCardHome)
        val subTitle: TextView = itemView.findViewById(R.id.tvSubTitleCardHome)
        val image: ImageView = itemView.findViewById(R.id.ivCardHome)
    }

    override fun getItemCount(): Int {
        return homeData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = homeData[position]
        holder.title.text = item.title
        holder.subTitle.text = item.subTitle
        Glide.with(context).load(item.image).placeholder(R.drawable.dummyimg)
            .error(R.drawable.dummyimg).skipMemoryCache(true).into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.each_card_home, parent, false)

        return ViewHolder(view)
    }
}