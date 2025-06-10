package com.training.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.easyfood.databinding.ActivityMealByCategoryBinding
import com.training.easyfood.databinding.MealbycategoryitemBinding
import com.training.easyfood.pojo.Meal
import com.training.easyfood.pojo.MealByCategory

class FavoriteMealAdapter() : RecyclerView.Adapter<FavoriteMealAdapter.FavoriteViewHolder>() {

    lateinit var onItemClick:((Meal) -> Unit)
    var deffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean {
            return oldItem==newItem
        }

    }

  val differ = AsyncListDiffer(this,deffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        return FavoriteViewHolder(MealbycategoryitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
       val mealList = differ.currentList[position]
        Glide.with(holder.itemView).load(mealList.strMealThumb).into(holder.binding.mealByCategoryImg)
        holder.binding.mealByCategoryName.text= mealList.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }
    }

    override fun getItemCount() = differ.currentList.size

    class FavoriteViewHolder(var binding: MealbycategoryitemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}