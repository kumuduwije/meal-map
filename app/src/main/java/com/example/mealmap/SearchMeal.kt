package com.example.mealmap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class  SearchMeal : Fragment() {


    @SuppressLint("MissingInflatedId", "CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_meal, container, false)
        val searchBtn :Button = view.findViewById(R.id.searchBtn  )


        searchBtn.setOnClickListener{
             val fragment = SearchMeal()
            val transition = fragmentManager?.beginTransaction()
            transition?.replace(R.id.meal_nav,fragment)?.commit()
        }
        return  view

    }

}