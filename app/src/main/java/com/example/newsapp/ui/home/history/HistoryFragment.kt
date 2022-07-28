package com.example.newsapp.ui.home.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.databinding.FragmentHistoryBinding
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.network.NetworkState

import com.example.newsapp.ui.home.headlines.HeadlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history), ArticleAdapter.ArticleInterface {

    private val viewModel: HeadlinesViewModel by viewModels()
    private lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var binding: FragmentHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        mArticleAdapter = ArticleAdapter(this)
        setViews()
        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchReadArticles().observe(viewLifecycleOwner) {
            mArticleAdapter.setData(ArrayList(it))
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
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(article.url)
        startActivity(intent)
    }
}