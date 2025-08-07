package com.training.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.easyfood.db.MealDataBase
import com.training.easyfood.pojo.Category
import com.training.easyfood.pojo.CategoryList
import com.training.easyfood.pojo.MealByCategoryList
import com.training.easyfood.pojo.MealByCategory
import com.training.easyfood.pojo.Meal
import com.training.easyfood.pojo.MealList
import com.training.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(
    var mealDataBase: MealDataBase
): ViewModel() {

    private var randomMeal = MutableLiveData<Meal>()
    private var mealByCategory = MutableLiveData<List<MealByCategory>>()
    private var categoryList = MutableLiveData<List<Category>>()
    private var mealFavoriteLiveData = mealDataBase.dao().getAllMeal()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()

    private var searchedMealLiveData =MutableLiveData<List<Meal>>()

    private var saveStateRandomMeal : Meal?=null

    fun getCategoryList(){
        RetrofitInstance.api.getCategoryList().enqueue(object : Callback<CategoryList>{
            override fun onResponse(
                call: Call<CategoryList?>,
                response: Response<CategoryList?>
            ) {
                if (response.body()!= null){
                    val category= response.body()!!.categories
                    categoryList.value=category
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

    fun observeCategoryList(): LiveData<List<Category>>{
        return categoryList
    }

    fun getRandomMeal(){
        saveStateRandomMeal?.let { randomMeals->
            randomMeal.postValue(randomMeals)
            return
        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList>{
            override fun onResponse(
                call: Call<MealList?>,
                response: Response<MealList?>
            ) {
                if (response.body() != null){
                    val meal :Meal= response.body()!!.meals[0]
                    randomMeal.value=meal
                    saveStateRandomMeal=meal
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
        RetrofitInstance.api.getPopularMeal("Seafood").enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(
                call: Call<MealByCategoryList?>,
                response: Response<MealByCategoryList?>
            ) {
                if (response.body()!=null){

                    val popularMeals = response.body()!!.meals
                    mealByCategory.value=popularMeals
                }else{
                    return
                }
            }

            override fun onFailure(
                call: Call<MealByCategoryList?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getMealById(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(
                call: Call<MealList?>,
                response: Response<MealList?>
            ) {
                val meal = response.body()?.meals[0]
                meal?.let { meal->
                    bottomSheetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(
                call: Call<MealList?>,
                t: Throwable
            ) {
                Log.e("HomeViewModel",t.message.toString())
            }

        })
    }

    fun searchMeal(searchQuery: String){
        RetrofitInstance.api.searchMeal(searchQuery).enqueue(object : Callback<MealList>{
            override fun onResponse(
                call: Call<MealList?>,
                response: Response<MealList?>
            ) {
                val mealList = response.body()?.meals
                mealList?.let {mealList->
                    searchedMealLiveData.postValue(mealList)
                }
            }

            override fun onFailure(
                call: Call<MealList?>,
                t: Throwable
            ) {
                Log.e("HomeViewModel",t.message.toString())
            }

        })
    }

    fun observeSearchedMealLiveData(): LiveData<List<Meal>>{
        return searchedMealLiveData
    }

    fun observeSheetLiveData(): LiveData<Meal>{
        return bottomSheetMealLiveData
    }
    fun observePopularMeal(): LiveData<List<MealByCategory>>{
        return mealByCategory
    }

    fun observeFavoriteLiveData() : LiveData<List<Meal>>{
        return mealFavoriteLiveData
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