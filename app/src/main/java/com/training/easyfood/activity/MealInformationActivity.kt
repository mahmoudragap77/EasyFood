package com.training.easyfood.activity

import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.training.easyfood.R
import com.training.easyfood.databinding.ActivityMealInformationBinding
import com.training.easyfood.fragment.HomeFragment

class MealInformationActivity : AppCompatActivity() {
    lateinit var mealId : String
    lateinit var mealName : String
    lateinit var mealThumb : String
    lateinit var binding : ActivityMealInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMEalInformation()
        setInformation()
    }

    private fun setInformation() {
        Glide.with(this)
            .load(mealThumb)
            .into(binding.collapseImg)

        binding.collapseToolBar.title=mealName
        binding.collapseToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapseToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMEalInformation() {
        val intent =intent
          mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
          mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}