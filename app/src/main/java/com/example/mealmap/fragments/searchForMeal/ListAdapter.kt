package com.example.mealmap.fragments.searchForMeal

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmap.R
import com.example.mealmap.data.Meal


class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mealName : TextView
        var category: TextView
        var area: TextView

//        fun bind(data : Meal){
//            mealName.text = data.meal
//            category.text = data.category
//            area.text = data.area
//        }

        init {
            mealName = itemView.findViewById(R.id.mealName)
            category = itemView.findViewById(R.id.category)
            area = itemView.findViewById(R.id.area)
        }
    }

//    private var mealList = emptyList<Meal>()
      private var mealList = listOf<Meal>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_meal_row, parent, false))

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = mealList[position]
        holder.mealName.text = currentItem.meal.toString()
        holder.category.text = currentItem.category.toString()
        holder.area.text = currentItem.area.toString()
//        holder.bind(mealList[position])

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(meal:List<Meal>){
        this.mealList = meal
        Log.d("this.mealList:::", this.mealList.size.toString())
        notifyDataSetChanged()
    }

}