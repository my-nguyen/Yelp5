package com.nguyen.yelp5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyen.yelp5.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val API_KEY = "7WnY3zVPZ8Yj9S8JWphAu5dn8myhk0N0eAZ4P5vluMBcEg7t1gc41fdBHgluTHLNziDGBiH0UvciG4-p-IJQfPvR5Pdhd9WJ1G4pQnwZr6_cZG54KU6rZVrjITSfX3Yx"

class MainActivity : AppCompatActivity() {
    @Inject lateinit var service: YelpService

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appGraph.inject(this)
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurants = mutableListOf<Restaurant>()
        val resAdapter = RestaurantsAdapter(restaurants)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = resAdapter
        }

        CoroutineScope(Dispatchers.Main).launch {
            val result = service.searchRestaurants("Bearer $API_KEY", "Avocado Toast", "New York")
            restaurants.addAll(result.businesses)
            resAdapter.notifyDataSetChanged()
        }
    }
}