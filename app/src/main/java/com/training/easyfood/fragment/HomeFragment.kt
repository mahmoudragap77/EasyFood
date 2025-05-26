package com.training.easyfood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.training.easyfood.databinding.FragmentHomeBinding
import com.training.easyfood.pojo.Meal
import com.training.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeMvvm: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeMvvm= ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeMvvm.getRandomMeal()
        observeRandomeMeal()
    }

    private fun observeRandomeMeal() {
        homeMvvm.observeRandomMealLiveData().observe ( viewLifecycleOwner, object: Observer<Meal>{
            override fun onChanged(value: Meal) {

                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgCardMeal)
            }

        })
    }


}