package com.example.newsapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticalBinding
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.util.convertToArticleDate
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class ArticleAdapter(private val articleInterface: ArticleInterface) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val data: ArrayList<ArticleModel> = ArrayList()

    fun setData(data: ArrayList<ArticleModel>) {
        this.data.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artical, parent, false)

        return ArticleViewHolder(view,articleInterface)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ArticleViewHolder(itemView: View,
    private val articleInterface: ArticleInterface) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SimpleDateFormat")
        fun bind(article: ArticleModel) {
            val binding = ItemArticalBinding.bind(itemView)
            binding.root.setOnClickListener {
                articleInterface.onClick(article)
            }
            Glide.with(binding.root.context).load(article.urlToImage).into(binding.ivArtical)
            binding.tvTitle.text = article.title
            binding.tvSource.text = article.source?.name
            binding.tvAuthor.text = article.author
            binding.tvPublishedAt.text = SimpleDateFormat("dd-MM-yyyy hh:mm a").format(
                convertToArticleDate(article.publishedAt)
            )
        }
    }

    interface  ArticleInterface{
        fun onClick(article: ArticleModel)
    }
}