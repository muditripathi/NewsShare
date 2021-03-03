package com.example.newsshare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

class NewsListAdapter(private val listner : NewsItemClicked): RecyclerView.Adapter<NewViewHolder>() {
    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewViewHolder(view)
        view.setOnClickListener {
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.authorView.text = currentItem.author
        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .placeholder(circularProgressDrawable)
            .into(holder.image)
    }

    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}

class NewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView : TextView = itemView.findViewById(R.id.title)
    val authorView : TextView = itemView.findViewById(R.id.author)
    val image : ImageView = itemView.findViewById(R.id.image)
}

interface NewsItemClicked{
    fun onItemClicked(item : News)
}