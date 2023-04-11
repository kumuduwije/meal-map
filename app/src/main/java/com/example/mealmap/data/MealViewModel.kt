package com.example.mealmap.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(application: Application): AndroidViewModel(application){

    private val readAllData : LiveData<List<Meal>>
    private val repository: MealRepository

    init{
        val mealDao = MealDatabase.getDatabase(application).mealDao()
        repository = MealRepository(mealDao)
        readAllData = repository.readAllMeals
    }

    fun addMeal (meal: Meal){
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.addMeal(meal)
        }
    }


}