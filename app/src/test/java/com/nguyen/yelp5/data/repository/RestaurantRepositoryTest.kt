package com.nguyen.yelp5.data.repository

import com.nguyen.yelp5.BuildConfig
import com.nguyen.yelp5.testutil.FakeYelpService
import com.nguyen.yelp5.testutil.restaurant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantRepositoryTest {

    @Test
    fun searchRestaurants_returnsBusinessesFromService() = runTest {
        val expected = listOf(restaurant("A"), restaurant("B"))
        val repository = RestaurantRepository(FakeYelpService { _, _ -> expected })

        assertEquals(expected, repository.searchRestaurants("Pizza", "San Francisco"))
    }

    @Test
    fun searchRestaurants_passesTermLocationAndBearerAuth() = runTest {
        val service = FakeYelpService()
        RestaurantRepository(service).searchRestaurants("Pizza", "San Francisco")

        assertEquals(
            listOf(FakeYelpService.Call("Bearer ${BuildConfig.YELP_API_KEY}", "Pizza", "San Francisco")),
            service.calls
        )
    }
}
