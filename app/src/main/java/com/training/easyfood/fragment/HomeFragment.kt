package com.training.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.easyfood.activity.MealInformationActivity
import com.training.easyfood.adapter.PopularAdapter
import com.training.easyfood.databinding.FragmentHomeBinding
import com.training.easyfood.pojo.CategoryMeal
import com.training.easyfood.pojo.Meal
import com.training.viewModel.HomeViewModel
import com.training.viewModel.MealViewModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeMvvm: HomeViewModel
    lateinit var randomMEal: Meal
    lateinit var mealMVVM : MealViewModel
    lateinit var popularAdapter: PopularAdapter

companion object{
    const val MEAL_ID  ="com.training.easyfood.fragment.idMeal"
    const  val MEAL_NAME  ="com.training.easyfood.fragment.nameMEal"
    const val MEAL_THUMB ="com.training.easyfood.fragment.thumbMeal"
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeMvvm= ViewModelProvider(this).get(HomeViewModel::class.java)
        popularAdapter= PopularAdapter()
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

        homeMvvm.getRandomMeal()
        observeRandomeMeal()
        onRandomMEalClick()
        homeMvvm.getPopulaMeal()
        observePopularMeal()

        onPopularMealClick()
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
        homeMvvm.observePopularMeal().observe(viewLifecycleOwner,object : Observer<List<CategoryMeal>>{
            override fun onChanged(value: List<CategoryMeal>) {

                popularAdapter.setMeal(mealList = value as ArrayList<CategoryMeal>)
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