package com.nguyen.yelp5

import android.app.Application
import com.nguyen.yelp5.di.DaggerAppGraph

class MyApplication : Application() {
    val appGraph = DaggerAppGraph.create()
}