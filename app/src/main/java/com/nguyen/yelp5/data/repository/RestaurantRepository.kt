package com.nguyen.yelp5.data.repository

import com.nguyen.yelp5.BuildConfig
import com.nguyen.yelp5.data.model.Restaurant
import com.nguyen.yelp5.data.remote.YelpService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(private val service: YelpService) {
    suspend fun searchRestaurants(term: String, location: String): List<Restaurant> =
        service.searchRestaurants("Bearer ${BuildConfig.YELP_API_KEY}", term, location).businesses
}
