package com.training.easyfood.retrofit

import com.training.easyfood.pojo.CategoryList
import com.training.easyfood.pojo.MealByCategoryList
import com.training.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id : String) :Call<MealList>

    @GET("filter.php?")
    fun getPopularMeal(@Query("c") category: String): Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategoryList(): Call<CategoryList>

    @GET("filter.php")
    fun getMealByCategory(@Query("c") mealName : String): Call<MealByCategoryList>

    @GET("search.php")
    fun searchMeal(@Query("s") mealName : String): Call<MealList>
}