package com.training.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.easyfood.databinding.PopularMealBinding
import com.training.easyfood.pojo.CategoryList
import com.training.easyfood.pojo.CategoryMeal

class PopularAdapter() : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    lateinit var onItemClick:((CategoryMeal) -> Unit)
    var mealList = ArrayList<CategoryMeal>()

    fun setMeal(mealList: ArrayList<CategoryMeal>){
        this.mealList=mealList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        return PopularViewHolder(PopularMealBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: PopularViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.popularMealImg)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }


    class PopularViewHolder( var binding: PopularMealBinding) : RecyclerView.ViewHolder(binding.root)
}