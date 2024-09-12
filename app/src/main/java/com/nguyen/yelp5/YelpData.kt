package com.nguyen.yelp5

data class YelpResult(val total: Int, val businesses: List<Restaurant>)

data class Restaurant(
    val name: String,
    val rating: Double,
    val price: String,
    val review_count: Int,
    val distance: Double,
    val image_url: String,
    val categories: List<Category>,
    val location: Location
    ) {
    fun toDistance(): String {
        val miles = "%.2f".format(distance * 0.000621371)
        return "$miles mi"
    }
}

data class Category(val title: String)

data class Location(val address1: String)