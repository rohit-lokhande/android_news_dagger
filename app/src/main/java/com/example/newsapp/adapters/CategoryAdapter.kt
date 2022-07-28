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
import com.example.newsapp.databinding.ItemCategoryBinding
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.models.CategoryModel
import com.example.newsapp.util.convertToArticleDate
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class CategoryAdapter(private val categoryInterface: CategoryInterface) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val data: ArrayList<CategoryModel> = ArrayList()

    fun setData(data: ArrayList<CategoryModel>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)

        return CategoryViewHolder(view,categoryInterface)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CategoryViewHolder(itemView: View,
    private val categoryInterface: CategoryInterface) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SimpleDateFormat")
        fun bind(category: CategoryModel) {
            val binding = ItemCategoryBinding.bind(itemView)
            binding.root.setOnClickListener {
                categoryInterface.onClick(category)
            }
            binding.tvCategory.text = category.title
            category.urlToImage?.let { binding.ivCategory.setBackgroundResource(it) }

        }
    }

    interface CategoryInterface{
        fun onClick(category:CategoryModel)
    }
}