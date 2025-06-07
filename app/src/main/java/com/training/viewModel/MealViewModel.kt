package com.training.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.easyfood.db.MealDataBase
import com.training.easyfood.pojo.Meal
import com.training.easyfood.pojo.MealList
import com.training.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDataBase: MealDataBase
) :ViewModel() {
 private var mealDetailLiveData = MutableLiveData<Meal>()

      fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(
                call: Call<MealList?>,
                response: Response<MealList?>
            ) {


                if (response.body()!= null){
                    mealDetailLiveData.value= response.body()?.meals[0]
                } else{
                    return
                }
            }

            override fun onFailure(
                call: Call<MealList?>,
                t: Throwable
            ) {
                Log.i("MAINACTIVITY",t.message.toString())
            }

        })
    }

    fun observeMealDetail(): LiveData<Meal>
    {
        return mealDetailLiveData
    }

    fun InsertMeal(meal: Meal){
       viewModelScope.launch {
           mealDataBase.dao().upsert(meal)
       }
    }
    fun deleteMeal(meal: Meal){
       viewModelScope.launch {
           mealDataBase.dao().delete(meal)
       }
    }

}