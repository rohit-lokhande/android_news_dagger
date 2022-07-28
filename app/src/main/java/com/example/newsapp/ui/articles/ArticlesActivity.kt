package com.example.newsapp.ui.articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.databinding.ActivityArticlesBinding
import com.example.newsapp.models.ArticleModel
import com.example.newsapp.network.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesActivity : AppCompatActivity(), ArticleAdapter.ArticleInterface {

    private val viewModel: ArticlesViewModel by viewModels()
    private lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var binding: ActivityArticlesBinding

    private val args: ArticlesActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mArticleAdapter = ArticleAdapter(this)
        setSupportActionBar(binding.toolbar)
        binding.appbarTitle.text = args.type.uppercase()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setViews()
        fetchData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun fetchData() {
        viewModel.fetchArticlesByCategory(args.type).observe(this) {
            mArticleAdapter.setData(it as ArrayList<ArticleModel>)
            mArticleAdapter.notifyDataSetChanged()
        }

        viewModel.networkStateLiveData.observe(this) {
            when (it) {
                NetworkState.LOADING -> {
                    binding.rvArticales.visibility = View.GONE
                    binding.shimmer.layoutShimmer.visibility = View.VISIBLE
                    binding.shimmer.layoutShimmer.startShimmerAnimation()
                }
                NetworkState.SUCCESS -> {
                    binding.rvArticales.visibility = View.VISIBLE
                    binding.shimmer.layoutShimmer.visibility = View.GONE
                    binding.shimmer.layoutShimmer.stopShimmerAnimation()
                }
                NetworkState.FAILURE -> {
                    Toast.makeText(applicationContext, "Execption", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private fun setViews() {
        binding.rvArticales.apply {
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