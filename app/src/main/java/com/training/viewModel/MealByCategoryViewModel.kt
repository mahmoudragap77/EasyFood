package com.training.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.training.easyfood.pojo.Meal
import com.training.easyfood.pojo.MealByCategory
import com.training.easyfood.pojo.MealByCategoryList
import com.training.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealByCategoryViewModel() : ViewModel() {

     val mealByCategoryLiveData = MutableLiveData<List<MealByCategory>>()

    fun getMealByCategory(mealName: String){
        RetrofitInstance.api.getMealByCategory(mealName).enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(
                call: Call<MealByCategoryList?>,
                response: Response<MealByCategoryList?>
            ) {
               response.body()?.let {mealList->
                   mealByCategoryLiveData.postValue(mealList.meals)
               }
            }

            override fun onFailure(
                call: Call<MealByCategoryList?>,
                t: Throwable
            ) {
                Log.i("MEALACTIVITY", t.message.toString())
            }

        })
    }

    fun observeMealByCategory(): LiveData<List<MealByCategory>>{
        return mealByCategoryLiveData
    }
}