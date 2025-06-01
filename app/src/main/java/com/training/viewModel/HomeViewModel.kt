package com.training.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.training.easyfood.pojo.CategoryList
import com.training.easyfood.pojo.CategoryMeal
import com.training.easyfood.pojo.Meal
import com.training.easyfood.pojo.MealList
import com.training.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private var randomMeal = MutableLiveData<Meal>()
    private var popularMeal = MutableLiveData<List<CategoryMeal>>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList>{
            override fun onResponse(
                call: Call<MealList?>,
                response: Response<MealList?>
            ) {
                if (response.body() != null){
                    val meal :Meal= response.body()!!.meals[0]
                    randomMeal.value=meal
                }
                else{
                    return
                }
            }

            override fun onFailure(
                call: Call<MealList?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal>{
        return randomMeal

    }

    fun getPopulaMeal(){
        RetrofitInstance.api.getPopularMeal("Seafood").enqueue(object : Callback<CategoryList> {
            override fun onResponse(
                call: Call<CategoryList?>,
                response: Response<CategoryList?>
            ) {
                if (response.body()!=null){

                    val popularMeals = response.body()!!.meals
                    popularMeal.value=popularMeals
                }else{
                    return
                }
            }

            override fun onFailure(
                call: Call<CategoryList?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observePopularMeal(): LiveData<List<CategoryMeal>>{
        return popularMeal
    }
}