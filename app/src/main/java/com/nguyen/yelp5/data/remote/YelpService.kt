package com.nguyen.yelp5.data.remote

import com.nguyen.yelp5.data.model.YelpResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    suspend fun searchRestaurants(
        @Header("Authorization") auth: String,
        @Query("term") term: String,
        @Query("location") location: String
    ): YelpResult
}