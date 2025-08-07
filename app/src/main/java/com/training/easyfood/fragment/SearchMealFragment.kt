package com.training.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.training.easyfood.activity.MainActivity
import com.training.easyfood.activity.MealInformationActivity
import com.training.easyfood.adapter.MealAdapter
import com.training.easyfood.databinding.FragmentSearchMealBinding
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_ID
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_NAME
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_THUMB
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

        onSearchedMealClick()


    }

    private fun onSearchedMealClick() {
            searchMealAdapter.onItemClick={ meal ->
                val intent = Intent(activity, MealInformationActivity::class.java)
                intent.putExtra(MEAL_ID,meal.idMeal)
                intent.putExtra(MEAL_NAME,meal.strMeal)
                intent.putExtra(MEAL_THUMB,meal.strMealThumb)
                startActivity(intent)
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
