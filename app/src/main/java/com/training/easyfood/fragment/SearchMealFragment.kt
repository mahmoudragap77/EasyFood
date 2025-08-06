package com.training.easyfood.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.training.easyfood.R
import com.training.easyfood.activity.MainActivity
import com.training.easyfood.adapter.MealAdapter
import com.training.easyfood.databinding.FragmentSearchMealBinding
import com.training.easyfood.pojo.Meal
import com.training.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchMealFragment : Fragment() {

    private lateinit var binding: FragmentSearchMealBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchMealAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel
         }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSearchMealBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        binding.arrowSearchId.setOnClickListener {
            searchMeal()

        }
        observeSearchMealLiveData()

        var searchJob : Job? = null
        binding.edSearchId.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
          searchJob = lifecycleScope.launch {
              delay(500)
              viewModel.searchMeal(searchQuery.toString())
          }
        }


    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchedMealLiveData().observe(
            viewLifecycleOwner
        ) { mealList ->
            searchMealAdapter.differ.submitList(mealList)
        }
    }

    private fun searchMeal() {
        val searchQuery = binding.edSearchId.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchMealAdapter = MealAdapter()
        binding.rvSearchId.apply {
            adapter = searchMealAdapter
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
        }
    }


}
