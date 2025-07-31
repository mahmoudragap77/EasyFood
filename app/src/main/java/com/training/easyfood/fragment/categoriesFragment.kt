package com.training.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.training.easyfood.activity.MainActivity
import com.training.easyfood.activity.MealByCategoryActivity
import com.training.easyfood.adapter.CategoryAdapter
import com.training.easyfood.databinding.FragmentCategoriesBinding
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_ID
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_NAME
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_THUMB
import com.training.viewModel.HomeViewModel


class categoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    lateinit var catAdapter: CategoryAdapter
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecycleView()
        observeCategory()
        onCategoryClick()
    }

    private fun onCategoryClick() {
            catAdapter.onItemClick={category ->
                val intent = Intent(activity, MealByCategoryActivity::class.java)
                intent.putExtra(CATEGORY_NAME,category.strCategory)
                startActivity(intent)
            }
        }


    private fun observeCategory() {
        viewModel.observeCategoryList().observe(viewLifecycleOwner) {
            catAdapter.setCategoryList(it)
        }
    }

    private fun prepareRecycleView() {
        catAdapter = CategoryAdapter()
        binding.rvFavorite.apply {
            adapter = catAdapter
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        }
    }

    companion object {
        const val CATEGORY_NAME ="com.training.easyfood.fragment.categoryName"
    }


}