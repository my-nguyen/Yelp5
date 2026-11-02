package com.nguyen.yelp5.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nguyen.yelp5.data.repository.RestaurantRepository
import com.nguyen.yelp5.testutil.FakeYelpService
import com.nguyen.yelp5.testutil.MainDispatcherRule
import com.nguyen.yelp5.testutil.restaurant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    private val results = mapOf(
        ("Avocado Toast" to "New York") to listOf(restaurant("Toast Spot")),
        ("Pizza" to "San Francisco") to listOf(restaurant("Pizza One"), restaurant("Pizza Two"))
    )
    private val service = FakeYelpService { term, location -> results.getValue(term to location) }
    private fun createViewModel() = MainViewModel(RestaurantRepository(service))

    @Test
    fun restaurants_isEmptyBeforeInitialSearchCompletes() {
        val viewModel = createViewModel()

        assertEquals(emptyList<Any>(), viewModel.restaurants.value)
    }

    @Test
    fun init_searchesAvocadoToastInNewYork() = runTest(mainDispatcherRule.dispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(listOf("Avocado Toast" to "New York"), service.calls.map { it.term to it.location })
        assertEquals(listOf(restaurant("Toast Spot")), viewModel.restaurants.value)
    }

    @Test
    fun search_replacesRestaurantsWithNewResults() = runTest(mainDispatcherRule.dispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.search("Pizza", "San Francisco")
        advanceUntilIdle()

        assertEquals(listOf(restaurant("Pizza One"), restaurant("Pizza Two")), viewModel.restaurants.value)
        assertEquals(2, service.calls.size)
    }
}
