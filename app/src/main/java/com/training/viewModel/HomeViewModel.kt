package com.training.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.training.easyfood.fragment.HomeFragment
import com.training.easyfood.pojo.Meal
import com.training.easyfood.pojo.MealList
import com.training.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private var randomMeal = MutableLiveData<Meal>()
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
}