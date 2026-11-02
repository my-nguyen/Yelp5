package com.nguyen.yelp5

import androidx.annotation.VisibleForTesting
import com.nguyen.yelp5.data.remote.YelpService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.yelp.com/v3/"

/** Creates app-wide dependencies by hand. */
object ServiceLocator {
    private val defaultYelpService: YelpService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YelpService::class.java)
    }

    /** Set by instrumented tests to replace the real Yelp API. */
    @VisibleForTesting
    @Volatile
    var yelpServiceOverride: YelpService? = null

    val yelpService: YelpService
        get() = yelpServiceOverride ?: defaultYelpService
}
