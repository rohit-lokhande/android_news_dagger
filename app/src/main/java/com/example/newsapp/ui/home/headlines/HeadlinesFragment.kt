package com.example.newsapp.ui.home.headlines

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.network.NetworkState

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HeadlinesFragment : Fragment(R.layout.fragment_headlines), ArticleAdapter.ArticleInterface {

    private val viewModel: HeadlinesViewModel by viewModels()
    private lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var binding: FragmentHeadlinesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesBinding.bind(view)
        mArticleAdapter = ArticleAdapter(this)
        setViews()
        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchTopHeadlines().observe(viewLifecycleOwner) {
            mArticleAdapter.setData(it as ArrayList<ArticleModel>)
            mArticleAdapter.notifyDataSetChanged()
        }

        viewModel.networkStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> {
                    binding.rvArticals.visibility = View.GONE
                    binding.shimmer.layoutShimmer.visibility = View.VISIBLE
                    binding.shimmer.layoutShimmer.startShimmerAnimation()
                }
                NetworkState.SUCCESS -> {
                    binding.rvArticals.visibility = View.VISIBLE
                    binding.shimmer.layoutShimmer.visibility = View.GONE
                    binding.shimmer.layoutShimmer.stopShimmerAnimation()
                }
                NetworkState.FAILURE -> {
                    Toast.makeText(context, "Execption", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private fun setViews() {
        binding.rvArticals.apply {
            setHasFixedSize(true)
            adapter = mArticleAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onClick(article: ArticleModel) {
        markAsRead(article)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(article.url)
        startActivity(intent)
    }

    private fun markAsRead(article: ArticleModel) {
        lifecycleScope.launch {
            viewModel.markArticleAsRead(article)
        }
    }
}