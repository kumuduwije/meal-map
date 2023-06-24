package com.example.mealmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mealmap.data.MealViewModel

/** @Author - Kumudu Wijewardhana
 * UoW Id : w1912845
 * IIT Id: 20210194
 **/

// Demonstration video link -> https://drive.google.com/drive/folders/1lU96g51JGqhq_xHX_ov5aiwQujno2ERu?usp=share_link

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment))

        viewModel = ViewModelProvider(this)[MealViewModel::class.java]


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}


// Reference:
// https://www.youtube.com/playlist?list=PLSrm9z4zp4mEPOfZNV9O-crOhoMa0G2-o - Room Database in kotlin and fragments
// https://stackoverflow.com/questions/59834398/android-navigation-component-back-button-not-working navigate back issue
// https://www.youtube.com/watch?v=orH4K6qBzvE&t=306s - ViewModel
// https://stackoverflow.com/questions/1748977/making-textview-scrollable-on-android - scrollable textview