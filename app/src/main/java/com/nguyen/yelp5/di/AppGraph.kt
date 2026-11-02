package com.nguyen.yelp5.di

import com.nguyen.yelp5.ui.main.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppGraph {
    fun inject(activity: MainActivity)
}
