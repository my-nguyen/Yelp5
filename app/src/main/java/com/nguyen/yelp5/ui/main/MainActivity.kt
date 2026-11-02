package com.nguyen.yelp5.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyen.yelp5.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
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
