package com.nguyen.yelp5.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nguyen.yelp5.R
import com.nguyen.yelp5.data.model.Category
import com.nguyen.yelp5.data.model.Location
import com.nguyen.yelp5.data.model.Restaurant
import com.nguyen.yelp5.ServiceLocator
import com.nguyen.yelp5.testutil.FakeYelpService
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val fakeService = FakeYelpService()

    private val toastSpot = Restaurant(
        name = "Toast Spot",
        rating = 4.5,
        price = "$$",
        review_count = 120,
        distance = 1609.344,
        image_url = "", // keep Glide off the network
        categories = listOf(Category("Cafe")),
        location = Location("123 Main St")
    )

    @Before
    fun setUp() {
        ServiceLocator.yelpServiceOverride = fakeService
    }

    @After
    fun tearDown() {
        ServiceLocator.yelpServiceOverride = null
    }

    @Test
    fun launch_searchesAvocadoToastInNewYork() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.recycler)).check(matches(isDisplayed()))
            assertEquals(listOf(FakeYelpService.Call("Avocado Toast", "New York")), fakeService.calls)
        }
    }

    @Test
    fun launch_displaysRestaurantDetails() {
        fakeService.businesses = listOf(toastSpot)

        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withText("Toast Spot")).check(matches(isDisplayed()))
            onView(withText("120 reviews")).check(matches(isDisplayed()))
            onView(withText("123 Main St")).check(matches(isDisplayed()))
            onView(withText("Cafe")).check(matches(isDisplayed()))
            onView(withText("$$")).check(matches(isDisplayed()))
            // Uses the same formatting as the app, so it passes regardless of device locale.
            onView(withText(toastSpot.toDistance())).check(matches(isDisplayed()))
        }
    }

    @Test
    fun launch_showsOneRowPerRestaurant() {
        fakeService.businesses = listOf(toastSpot, toastSpot.copy(name = "Brunch Bar"), toastSpot.copy(name = "Cafe Uno"))

        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withText("Brunch Bar")).check(matches(isDisplayed()))
            onView(withId(R.id.recycler)).check { view, noViewFound ->
                if (noViewFound != null) throw noViewFound
                assertEquals(3, (view as RecyclerView).adapter?.itemCount)
            }
        }
    }

    @Test
    fun launch_withNoResults_showsEmptyList() {
        fakeService.businesses = emptyList()

        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.recycler)).check { view, noViewFound ->
                if (noViewFound != null) throw noViewFound
                assertEquals(0, (view as RecyclerView).adapter?.itemCount)
            }
        }
    }
}
