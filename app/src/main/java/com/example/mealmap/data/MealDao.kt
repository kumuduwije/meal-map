package com.example.mealmap.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun addMeal(meal: Meal)

    @Query("SELECT * FROM meals")
    fun readAllMeals(): LiveData<List<Meal>>
}