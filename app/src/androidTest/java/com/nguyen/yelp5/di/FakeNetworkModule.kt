package com.nguyen.yelp5.di

import com.nguyen.yelp5.data.model.Restaurant
import com.nguyen.yelp5.data.model.YelpResult
import com.nguyen.yelp5.data.remote.YelpService
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject
import javax.inject.Singleton

/** Records calls and returns [businesses] instead of hitting the real Yelp API. */
@Singleton
class FakeYelpService @Inject constructor() : YelpService {
    data class Call(val term: String, val location: String)

    var businesses: List<Restaurant> = emptyList()
    val calls = mutableListOf<Call>()

    override suspend fun searchRestaurants(auth: String, term: String, location: String): YelpResult {
        calls += Call(term, location)
        return YelpResult(businesses.size, businesses)
    }
}

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [NetworkModule::class])
abstract class FakeNetworkModule {
    @Binds
    abstract fun bindYelpService(fake: FakeYelpService): YelpService
}
