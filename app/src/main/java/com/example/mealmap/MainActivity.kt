package com.example.mealmap

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealmap.data.Ingredient
import com.example.mealmap.data.Meal
import com.example.mealmap.data.MealViewModel
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

// https://www.youtube.com/playlist?list=PLSrm9z4zp4mEPOfZNV9O-crOhoMa0G2-o - Room Database in kotlin

class MainActivity : AppCompatActivity() {

    private lateinit var addToDbBtn: Button
    private lateinit var view: TextView
    private lateinit var viewModel: MealViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        addToDbBtn = findViewById(R.id.saveToDbBtn)
        view = findViewById(R.id.textView)
        viewModel = ViewModelProvider(this).get(MealViewModel::class.java)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, SearchMeal()).commit()




        //collecting all the JSON String
        var stb = java.lang.StringBuilder()

       // val urlString = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=15"
        val urlString = "https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata"
        val url = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection



        addToDbBtn.setOnClickListener {
            insertDataToDatabase()
        }

        runBlocking {
            launch {
                // run the code of the coroutine in a new thread
                withContext(Dispatchers.IO) {
                    val bf = BufferedReader(InputStreamReader(connection.inputStream))
                    print("bf::$bf")
                   var line: String? = bf.readLine()
                    while (line != null) {
                        stb.append(line + "\n")
                       line = bf.readLine()
                    }
                    bf.close()
//                    parseJSON(stb)
                    Log.d("Stb :",stb.toString())
                }

            }
        }
        // Make API call using HttpURLConnection
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata")
//                val connection = url.openConnection() as HttpURLConnection
//                connection.requestMethod = "GET"
//
//                val responseCode = connection.responseCode
//                //Log.d("responseCode", responseCode.toString())
//                println("responseCode$responseCode")
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
//                    val response = StringBuffer()
//                    var line: String?
//                    while (reader.readLine().also { line = it } != null) {
//                        response.append(line)
//                    }
//                    reader.close()
//
//                    val json = JSONObject(response.toString())
//                    Log.d("MealDB", json.toString())
//                } else {
//                    Log.e("MealDB", "Error fetching meal")
//                }
//            } catch (e: Exception) {
//                Log.e("MealDB", "Error fetching meal", e)
//            }
//        }



    }



    private suspend  fun parseJSON(stb: java.lang.StringBuilder) {
        // this contains the full JSON returned by the Web Service
        val json = JSONObject(stb.toString())
        // Information about all the books extracted by this function
        val allBooks = java.lang.StringBuilder()
        val jsonArray:JSONArray = json.getJSONArray("items")
        // extract all the books from the JSON array
        for (i in 0 until jsonArray.length()) {
            val book: JSONObject = jsonArray[i] as JSONObject // this is a json object
            // extract the title
            val volInfo = book["volumeInfo"] as JSONObject
            val title = volInfo["title"] as String
            allBooks.append("${i+1}) \"$title\" ")
            // extract all the authors
            val authors = volInfo["authors"] as JSONArray
            allBooks.append("authors: ")

            for (i in 0 until authors.length())
                allBooks.append(authors[i] as String + ", ")
            allBooks.append("\n\n")
        }
        view.append(allBooks)
        print("All books: $allBooks")
    }





    private fun allInstructions(): List<String> {

        val instruction1 =
            "Preparation\\r\\n1. Crack the egg into a bowl. Separate the egg white and yolk.\\r\\n\\r\\nSweet and Sour Pork\\r\\n2. Slice the pork tenderloin into ips.\\r\\n\\r\\n3. " +
                    "Prepare the marinade using a pinch of salt, one teaspoon of starch, " +
                    "two teaspoons of light soy sauce, and an egg white.\\r\\n\\r\\n4. " +
                    "Marinade the pork ips for about 20 minutes.\\r\\n\\r\\n5. " +
                    "Put the remaining starch in a bowl. " +
                    "Add some water and vinegar to make a starchy sauce.\\r\\n\\r\\nSweet and Sour Pork\\r\\nCooking Inuctions\\r\\n1. " +
                    "Pour the cooking oil into a wok and heat to 190\\u00b0C (375\\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\\r\\n\\r\\n2. Leave some oil in the wok. " +
                    "Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\\r\\n\\r\\n3. " +
                    "Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\\r\\n\\r\\n4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\\r\\n\\r\\n5. Serve on a plate and add some coriander for decoration."


        val instruction2 =
            "Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. " +
                    "Add the chicken legs and cook briefly on each side to colour them a little." +
                    "\r\nPour in the passata, crumble in the stock cube and stir in the olives. " +
                    "Season with black pepper \u2013 you shouldn\u2019t need salt. Cover and simmer for 40 mins until the chicken is tender. " +
                    "Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like."

        val instruction3 =
            "Add'l ingredients: mayonnaise, siracha\r\n\r\n1\r\n\r\nPlace rice in a fine-mesh sieve and rinse until water runs clear. " +
                    "Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. " +
                    "Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. " +
                    "Keep covered off heat for at least 10 minutes or until ready to serve." +
                    "\r\n\r\n2\r\n\r\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. " +
                    "Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). " +
                    "Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot.\r\n\r\n3\r\n\r\nIn a medium bowl, combine cucumber, juice from half the lime, \u00bc tsp sugar (\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\u2019d like. Season with salt and pepper.\r\n\r\n4\r\n\r\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\r\n\r\n5\r\n\r\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo."

        val instruction4 =
            "Heat the oil in a large pot. Add the onion and cook until translucent.\r\nDrain the soaked chickpeas and add them to the pot together with the vegetable stock. " +
                    "Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes." +
                    "\r\nIn the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. " +
                    "Add the garlic and salt and pound to a fine paste.\r\nAdd the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\r\nSeason to taste with salt, pepper and lemon juice and serve hot."

        return listOf(instruction1, instruction2, instruction3, instruction4)

    }

    private fun allIngredients(): List<List<Ingredient>> {

        // Add the ingredients to a list
        val ingredientsList1 = listOf(
            Ingredient(name = "Pork", measurement = "200g"),
            Ingredient(name = "Egg", measurement = "1"),
            Ingredient(name = "Water", measurement = "Dash"),
            Ingredient(name = "Salt", measurement = "1/2 tsp"),
            Ingredient(name = "Soy Sauce", measurement = "10g"),
            Ingredient(name = "Starch", measurement = "10g"),
            Ingredient(name = "Starch", measurement = "10g"),
            Ingredient(name = "Tomato Puree", measurement = "30g"),
            Ingredient(name = "Vinegar", measurement = "10g"),
            Ingredient(name = "Coriander", measurement = "Dash")
        )

        val ingredientsList2 = listOf(
            Ingredient(name = "Olive Oil", measurement = "1 tbs"),
            Ingredient(name = "Mushrooms", measurement = "300g"),
            Ingredient(name = "Chicken Legs", measurement = "4"),
            Ingredient(name = "Passata", measurement = "500g"),
            Ingredient(name = "Chicken Stock Cube", measurement = "1"),
            Ingredient(name = "Black Olives", measurement = "100g"),
            Ingredient(name = "Parsley", measurement = "Chopped"),

        )

        val ingredientsList3 = listOf(
            Ingredient(name = "Rice", measurement = "White"),
            Ingredient(name = "Onion", measurement = "1"),
            Ingredient(name = "Lime", measurement = "1"),
            Ingredient(name = "Garlic Clove", measurement = "3"),
            Ingredient(name = "Cucumber", measurement = "1"),
            Ingredient(name = "Carrots", measurement = "3 oz"),
            Ingredient(name = "Ground Beef", measurement = "1 lb"),
            Ingredient(name = "Soy Sauce", measurement = "2 oz"),
        )

        val ingredientsList4 = listOf(
            Ingredient(name = "Olive Oil", measurement = "2 tbs"),
            Ingredient(name = "Onion", measurement = "1 medium finely diced"),
            Ingredient(name = "Chickpeas", measurement = "250g"),
            Ingredient(name = "Vegetable Stock", measurement = "1.5L"),
            Ingredient(name = "Cumin", measurement = "1 tsp"),
            Ingredient(name = "Garlic", measurement = "5 cloves"),
            Ingredient(name = "Salt", measurement = "1\\/2 tsp"),
            Ingredient(name = "Harissa Spice", measurement = "1 tsp"),
            Ingredient(name = "Pepper", measurement = "Pinch"),
            Ingredient(name = "Lime", measurement = "1\\/2 "),

            )

        return listOf(ingredientsList1, ingredientsList2, ingredientsList3, ingredientsList4)
    }

    private fun insertDataToDatabase(){

        val instructionList = allInstructions()
        val ingredientList = allIngredients()

         val meal1 = Meal(1,
             "Sweet and Sour Pork","",
             "Pork","Chinese", instructionList[0],
             "https://www.themealdb.com/images/media/meals/1529442316.jpg",
             "Sweet","https://www.youtube.com/watch?v=mdaBIhgEAMo", ingredientList[0],
             "","","","")

        val meal2 = Meal(2,
            "Chicken Marengo","",
            "Chicken","French", instructionList[1],
            "https://www.themealdb.com/images/media/meals/qpxvuq1511798906.jpg",
            " ","https://www.youtube.com/watch?v=U33HYUr-0Fw", ingredientList[1],
            "","","","")

        val meal3 = Meal(3,
            "Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber",
            "",
            "Beef","Vietnamese", instructionList[2],
            "https://www.themealdb.com/images/media/meals/z0ageb1583189517.jpg",
            " ","", ingredientList[2],
            "","","","")

        val meal4 = Meal(4,
            "Leblebi Soup",
            "",
            "Vegetarian","Tunisian", instructionList[3],
            "https://www.themealdb.com/images/media/meals/x2fw9e1560460636.jpg",
            "Soup","", ingredientList[3],
            "http://allrecipes.co.uk/recipe/43419/leblebi--tunisian-chickpea-soup-.aspx",
            "","","")


        viewModel.addMeal(meal1)
        viewModel.addMeal(meal2)
        viewModel.addMeal(meal3)
        viewModel.addMeal(meal4)



        Toast.makeText(this,"Successfully added.",Toast.LENGTH_SHORT).show()
    }
}