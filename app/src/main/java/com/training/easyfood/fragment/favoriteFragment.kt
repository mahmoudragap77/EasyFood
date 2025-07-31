package com.training.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.training.easyfood.R
import com.training.easyfood.activity.MainActivity
import com.training.easyfood.activity.MealInformationActivity
import com.training.easyfood.adapter.FavoriteMealAdapter
import com.training.easyfood.databinding.FragmentFavoriteBinding
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_ID
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_NAME
import com.training.easyfood.fragment.HomeFragment.Companion.MEAL_THUMB
import com.training.easyfood.pojo.Meal
import com.training.viewModel.HomeViewModel

class favoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var viewModel: HomeViewModel
    lateinit var favoriteMealAdapter: FavoriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecycleView()
        onserveFavoriteMeal()
        onFavoriteMealClick()
        itemDelete()

    }

    private fun itemDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target:RecyclerView.ViewHolder
            ) =true

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoriteMealAdapter.differ.currentList[position]
                viewModel.deleteMeal(favoriteMealAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal Deleted Do You Want to....",Snackbar.LENGTH_LONG).setAction(
                    "Undo",View.OnClickListener {
                        viewModel.InsertMeal(deletedMeal)
                    }
                ).show()
            }
    }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorite)

    }

    private fun onFavoriteMealClick() {
        favoriteMealAdapter.onItemClick={meal ->
            val intent = Intent(activity, MealInformationActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecycleView() {
        favoriteMealAdapter = FavoriteMealAdapter()
        binding.rvFavorite.apply {
            adapter=favoriteMealAdapter
            layoutManager= GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
        }
    }

    private fun onserveFavoriteMeal() {
       viewModel.observeFavoriteLiveData().observe(viewLifecycleOwner,object : Observer<List<Meal>>{
           override fun onChanged(value: List<Meal>) {
                   favoriteMealAdapter.differ.submitList(value)
           }

       })
         }

}