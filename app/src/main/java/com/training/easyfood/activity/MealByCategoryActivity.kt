package com.training.easyfood.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.training.easyfood.R
import com.training.easyfood.adapter.MealByCategoryAdapter
import com.training.easyfood.databinding.ActivityMealByCategoryBinding
import com.training.easyfood.fragment.HomeFragment
import com.training.easyfood.pojo.MealByCategory
import com.training.viewModel.MealByCategoryViewModel

class MealByCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityMealByCategoryBinding
    lateinit var mealByCategoryViewModel: MealByCategoryViewModel
    lateinit var mealByCategoryAdapter: MealByCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMealByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preparRecView()
        mealByCategoryViewModel= ViewModelProvider(this).get(MealByCategoryViewModel::class.java)

        val mealByCategoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!

        mealByCategoryViewModel.getMealByCategory(mealByCategoryName)
        observeMealByCategory()

    }

    private fun preparRecView() {
        mealByCategoryAdapter= MealByCategoryAdapter()
        binding.mealsByCategoryRv.apply {
            layoutManager= GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter=mealByCategoryAdapter
        }
    }

    private fun observeMealByCategory() {
        mealByCategoryViewModel.observeMealByCategory().observe(this , object : Observer<List<MealByCategory>>{

            override fun onChanged(value: List<MealByCategory>) {

                binding.numberOfMealTxt.text= value.size.toString()
                mealByCategoryAdapter.setMealByCategory(value)
            }

        })
    }
}