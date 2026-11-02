package com.nguyen.yelp5.data.model

import com.nguyen.yelp5.testutil.restaurant
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Locale

class RestaurantTest {
    private lateinit var originalLocale: Locale

    @Before
    fun setUp() {
        // toDistance() formats with the default locale; pin it so results are deterministic.
        originalLocale = Locale.getDefault()
        Locale.setDefault(Locale.US)
    }

    @After
    fun tearDown() = Locale.setDefault(originalLocale)

    @Test
    fun toDistance_convertsOneMileOfMeters() {
        assertEquals("1.00 mi", restaurant(distance = 1609.344).toDistance())
    }

    @Test
    fun toDistance_zeroMeters() {
        assertEquals("0.00 mi", restaurant(distance = 0.0).toDistance())
    }

    @Test
    fun toDistance_roundsToTwoDecimals() {
        // 1000 m = 0.621371 mi
        assertEquals("0.62 mi", restaurant(distance = 1000.0).toDistance())
    }
}
