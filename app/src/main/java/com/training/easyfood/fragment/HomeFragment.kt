package com.training.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.training.easyfood.activity.MealByCategoryActivity
import com.training.easyfood.activity.MealInformationActivity
import com.training.easyfood.adapter.CategoryAdapter
import com.training.easyfood.adapter.PopularAdapter
import com.training.easyfood.databinding.FragmentHomeBinding
import com.training.easyfood.pojo.Category
import com.training.easyfood.pojo.MealByCategory
import com.training.easyfood.pojo.Meal
import com.training.viewModel.HomeViewModel
import com.training.viewModel.MealViewModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeMvvm: HomeViewModel
    lateinit var randomMEal: Meal
    lateinit var mealMVVM : MealViewModel
    lateinit var popularAdapter: PopularAdapter
    lateinit var categoryAdapter : CategoryAdapter

companion object{
    const val MEAL_ID  ="com.training.easyfood.fragment.idMeal"
    const  val MEAL_NAME  ="com.training.easyfood.fragment.nameMEal"
    const val MEAL_THUMB ="com.training.easyfood.fragment.thumbMeal"
    const val CATEGORY_NAME ="com.training.easyfood.fragment.categoryName"
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeMvvm= ViewModelProvider(this).get(HomeViewModel::class.java)
        popularAdapter= PopularAdapter()
        categoryAdapter= CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recViewPopular.apply {
            adapter= popularAdapter
            layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        }

        binding.recCategory.apply {
            adapter=categoryAdapter
            layoutManager= GridLayoutManager(activity,3, GridLayoutManager.VERTICAL,false)
        }

        homeMvvm.getRandomMeal()
        observeRandomeMeal()
        onRandomMEalClick()

        homeMvvm.getPopulaMeal()
        observePopularMeal()
        onPopularMealClick()

        homeMvvm.getCategoryList()
        observeCategoryList()
        onCategoryListClick()
    }

    private fun onCategoryListClick() {
        categoryAdapter.onItemClick= {
            category ->
            val intent = Intent(activity, MealByCategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategoryList() {
        homeMvvm.observeCategoryList().observe(viewLifecycleOwner, object : Observer<List<Category>>{
            override fun onChanged(value: List<Category>) {
                categoryAdapter.setCategoryList(categoryList = value as ArrayList<Category>)
            }

        })
    }

    private fun onPopularMealClick() {
        popularAdapter.onItemClick={meal ->
            val intent = Intent(activity, MealInformationActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observePopularMeal() {
        homeMvvm.observePopularMeal().observe(viewLifecycleOwner,object : Observer<List<MealByCategory>>{
            override fun onChanged(value: List<MealByCategory>) {

                popularAdapter.setMeal(mealList = value as ArrayList<MealByCategory>)
            }

        })
    }

    private fun onRandomMEalClick() {
        binding.imgCardMeal.setOnClickListener {

            val intent = Intent(activity, MealInformationActivity::class.java)
            intent.putExtra(MEAL_ID,randomMEal.idMeal)
            intent.putExtra(MEAL_NAME,randomMEal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMEal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomeMeal() {
        homeMvvm.observeRandomMealLiveData().observe ( viewLifecycleOwner,
            {meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgCardMeal)
                this.randomMEal=meal
        })
    }


}