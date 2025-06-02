package com.training.easyfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.easyfood.databinding.CategoryListBinding
import com.training.easyfood.pojo.Category
import com.training.easyfood.pojo.CategoryList
import com.training.easyfood.pojo.PopularMeal

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

//    lateinit var onItemClick:((Category) -> Unit)
    var categoriList = ArrayList<Category>()


    fun setCategoryList(categoryList: List<Category>){
        this.categoriList=categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {

        return CategoryViewHolder(CategoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(categoriList[position].strCategoryThumb)
            .into(holder.binding.categoryListImg)

        holder.binding.categoryText.text=categoriList[position].strCategory

//        holder.itemView.setOnClickListener {
//            onItemClick.invoke(categoriList[position])
//        }
    }

    override fun getItemCount(): Int {
        return categoriList.size
    }

    class CategoryViewHolder(var binding: CategoryListBinding) : RecyclerView.ViewHolder(binding.root)
}