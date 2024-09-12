package com.nguyen.yelp5

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