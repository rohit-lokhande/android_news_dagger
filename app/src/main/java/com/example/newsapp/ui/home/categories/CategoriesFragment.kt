package com.example.newsapp.ui.home.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.CategoryAdapter
import com.example.newsapp.databinding.FragmentCategoriesBinding
import com.example.newsapp.models.CategoryModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories),
    CategoryAdapter.CategoryInterface {

    private lateinit var mCategoryAdapter: CategoryAdapter
    private lateinit var binding: FragmentCategoriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        mCategoryAdapter = CategoryAdapter(this)
        setViews()
        setData()
    }

    private fun setData() {
        val categories: ArrayList<CategoryModel> = ArrayList()
        categories.add(
            CategoryModel(
                1,
                "Business",
                R.drawable.ic_business,
                "business"
            )
        )

        categories.add(
            CategoryModel(
                2,
                "Entertainment",
                R.drawable.ic_entertainment,
                "entertainment"
            )
        )

        categories.add(
            CategoryModel(
                3,
                "Health",
                R.drawable.ic_health,
                "health"
            )
        )

        categories.add(
            CategoryModel(
                4,
                "Science",
                R.drawable.ic_science,
                "science"
            )
        )

        categories.add(
            CategoryModel(
                4,
                "Sports",
                R.drawable.ic_sports,
                "sports"
            )
        )

        mCategoryAdapter.setData(categories)
    }

    private fun setViews() {
        binding.rvCatrgory.apply {
            setHasFixedSize(true)
            adapter = mCategoryAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onClick(category: CategoryModel) {
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToArticlesActivity()
        action.type = category.type.toString()
        view?.findNavController()?.navigate(action)
    }


}