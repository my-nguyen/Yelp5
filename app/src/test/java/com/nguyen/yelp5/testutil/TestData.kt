package com.nguyen.yelp5.testutil

import com.nguyen.yelp5.data.model.Category
import com.nguyen.yelp5.data.model.Location
import com.nguyen.yelp5.data.model.Restaurant
import com.nguyen.yelp5.data.model.YelpResult
import com.nguyen.yelp5.data.remote.YelpService

fun restaurant(name: String = "Charley St", distance: Double = 1609.344) = Restaurant(
    name = name,
    rating = 4.5,
    price = "$$",
    review_count = 120,
    distance = distance,
    image_url = "https://example.com/$name.jpg",
    categories = listOf(Category("Cafe")),
    location = Location("123 Main St")
)

/** Records every call and returns whatever [respond] produces for the given term/location. */
class FakeYelpService(
    private val respond: (term: String, location: String) -> List<Restaurant> = { _, _ -> emptyList() }
) : YelpService {
    data class Call(val auth: String, val term: String, val location: String)

    val calls = mutableListOf<Call>()

    override suspend fun searchRestaurants(auth: String, term: String, location: String): YelpResult {
        calls += Call(auth, term, location)
        val businesses = respond(term, location)
        return YelpResult(total = businesses.size, businesses = businesses)
    }
}
