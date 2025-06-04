package com.training.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.easyfood.databinding.MealbycategoryitemBinding
import com.training.easyfood.pojo.MealByCategory

class MealByCategoryAdapter() :
    RecyclerView.Adapter<MealByCategoryAdapter.MealByCategoryViewHolder>() {

    var mealByCategoryList = ArrayList<MealByCategory>()

    fun setMealByCategory(mealByCategoryList: List<MealByCategory>) {
        this.mealByCategoryList = mealByCategoryList as ArrayList<MealByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealByCategoryViewHolder {
        return MealByCategoryViewHolder(
            MealbycategoryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MealByCategoryViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(mealByCategoryList[position].strMealThumb)
            .into(holder.binding.mealByCategoryImg)

        holder.binding.mealByCategoryName.text = mealByCategoryList[position].strMeal
    }

    override fun getItemCount() = mealByCategoryList.size


    inner class MealByCategoryViewHolder(var binding: MealbycategoryitemBinding) :
        RecyclerView.ViewHolder(binding.root)
}