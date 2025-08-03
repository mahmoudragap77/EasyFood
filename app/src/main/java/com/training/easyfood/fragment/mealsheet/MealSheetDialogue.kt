package com.training.easyfood.fragment.mealsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.training.easyfood.R
import com.training.easyfood.activity.MainActivity
import com.training.easyfood.activity.MealInformationActivity
import com.training.easyfood.databinding.FragmentMealSheetDialogueBinding
import com.training.easyfood.fragment.HomeFragment
import com.training.viewModel.HomeViewModel
import kotlin.jvm.java

private const val MEAL_ID = "param1"




class MealSheetDialogue : BottomSheetDialogFragment() {
    private var mealId: String? = null

    private lateinit var binding: FragmentMealSheetDialogueBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            arguments?.let {
                mealId=it.getString(MEAL_ID)
            }
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealSheetDialogueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }

        observeBottomSheetMeal()
        onBottomSheetDialogueClick()
    }

    private fun onBottomSheetDialogueClick() {
        binding.sheetDialogueIde.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealInformationActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)


            }
        }

    }

    private var mealName: String? = null
    private var mealThumb: String? = null

    private fun observeBottomSheetMeal() {
        viewModel.observeSheetLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgSheetId)
            binding.sheetPlaceId.text = meal.strArea
            binding.sheetCategoryId.text = meal.strCategory
            binding.sheetMealNameId.text = meal.strMeal

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealSheetDialogue().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}