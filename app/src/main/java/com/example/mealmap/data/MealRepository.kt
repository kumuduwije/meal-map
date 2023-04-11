package com.example.mealmap.data


import androidx.lifecycle.LiveData


class MealRepository(private val mealDao: MealDao) {
    val readAllMeals : LiveData<List<Meal>> = mealDao.readAllMeals()

    suspend fun addMeal (meal: Meal)  {
        mealDao.addMeal(meal)
    }



}