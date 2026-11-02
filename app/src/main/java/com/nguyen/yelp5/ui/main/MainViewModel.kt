package com.nguyen.yelp5.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nguyen.yelp5.data.model.Restaurant
import com.nguyen.yelp5.data.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>(emptyList())
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    init {
        search("Avocado Toast", "New York")
    }

    fun search(term: String, location: String) {
        viewModelScope.launch {
            _restaurants.value = repository.searchRestaurants(term, location)
        }
    }
}
