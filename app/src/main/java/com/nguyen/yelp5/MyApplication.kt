package com.nguyen.yelp5

import android.app.Application

class MyApplication : Application() {
    val appGraph = DaggerAppGraph.create()
}