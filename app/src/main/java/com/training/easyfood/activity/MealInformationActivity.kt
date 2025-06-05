package com.training.easyfood.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.training.easyfood.R
import com.training.easyfood.databinding.ActivityMealInformationBinding
import com.training.easyfood.fragment.HomeFragment
import com.training.easyfood.pojo.Meal
import com.training.viewModel.MealViewModel

class MealInformationActivity : AppCompatActivity() {
    lateinit var mealId : String
    lateinit var mealName : String
    lateinit var mealThumb : String
    lateinit var binding : ActivityMealInformationBinding
    lateinit var mealMVVM : MealViewModel
     var youTubeLink : String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMVVM= ViewModelProvider(this).get(MealViewModel::class.java)

        getMEalInformation()
        setInformation()

        onLoading()
        mealMVVM.getMealDetail(mealId)
        observeMealDetail()
        openyouTubeLink()
    }

    private fun openyouTubeLink() {
        binding.youtube.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
        startActivity(intent)
        }
    }

    private fun observeMealDetail() {
        mealMVVM.observeMealDetail().observe(this,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onSuccess()
                val meal = value
                binding.categoryText.text="Category :${meal!!.strCategory}"
                binding.areaText.text="Area : ${meal!!.strArea}"
                binding.mealDetail.text=meal.strInstructions
                youTubeLink=meal.strYoutube
            }

        })
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

    private fun onLoading(){
        binding.SHOWPROGRESS.visibility= View.VISIBLE
        binding.txInstructions.visibility= View.INVISIBLE
        binding.linearLayout.visibility= View.INVISIBLE
        binding.floating.visibility= View.INVISIBLE
        binding.youtube.visibility= View.INVISIBLE
    }
    private fun onSuccess(){
        binding.SHOWPROGRESS.visibility= View.INVISIBLE
        binding.txInstructions.visibility= View.VISIBLE
        binding.linearLayout.visibility= View.VISIBLE
        binding.floating.visibility= View.VISIBLE
        binding.youtube.visibility= View.VISIBLE
    }
}