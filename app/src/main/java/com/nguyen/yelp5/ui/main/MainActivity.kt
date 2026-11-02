package com.nguyen.yelp5.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyen.yelp5.MyApplication
import com.nguyen.yelp5.data.repository.RestaurantRepository
import com.nguyen.yelp5.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var repository: RestaurantRepository

    private val viewModel: MainViewModel by viewModels { MainViewModel.factory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appGraph.inject(this)
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resAdapter = RestaurantsAdapter()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = resAdapter
        }

        viewModel.restaurants.observe(this) { resAdapter.submit(it) }
    }
}
