package com.nguyen.yelp5

import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppGraph {
    fun inject(activity: MainActivity)
}