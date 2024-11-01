package com.aruel.mvvmnewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aruel.mvvmnewsapp.Events
import com.aruel.mvvmnewsapp.R
import com.bumptech.glide.Glide


class EventAdapter : RecyclerView.Adapter<EventAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivArticleImage: ImageView = itemView.findViewById(R.id.ivArticleImage)
        val tvSource: TextView? = itemView.findViewById(R.id.tvSource)
        val tvTitle: TextView? = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView? = itemView.findViewById(R.id.tvDescription)
        val tvPublishedAt: TextView? = itemView.findViewById(R.id.tvPublishedAt)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Events>() {
        override fun areItemsTheSame(oldItem: Events, newItem: Events): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: Events, newItem: Events): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.apply {
            // Use null-safe calls to handle nullable viewsarticle.name
            Glide.with(itemView).load(article.mediaCover).into(ivArticleImage)
            tvSource?.text = article.name
            tvTitle?.text = article.ownerName
            tvDescription?.text = article.cityName
            tvPublishedAt?.text = article.beginTime

            itemView.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener: ((Events) -> Unit)? = null

    fun setOnItemClickListener(listener: (Events) -> Unit) {
        onItemClickListener = listener
    }
}
