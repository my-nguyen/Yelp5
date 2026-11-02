package com.nguyen.yelp5.testutil

import com.nguyen.yelp5.data.model.Restaurant
import com.nguyen.yelp5.data.model.YelpResult
import com.nguyen.yelp5.data.remote.YelpService

/** Records calls and returns [businesses] instead of hitting the real Yelp API. */
class FakeYelpService : YelpService {
    data class Call(val term: String, val location: String)

    var businesses: List<Restaurant> = emptyList()
    val calls = mutableListOf<Call>()

    override suspend fun searchRestaurants(auth: String, term: String, location: String): YelpResult {
        calls += Call(term, location)
        return YelpResult(businesses.size, businesses)
    }
}
