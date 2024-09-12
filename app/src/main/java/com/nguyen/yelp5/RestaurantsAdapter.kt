package com.nguyen.yelp5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.nguyen.yelp5.databinding.ItemRestaurantBinding

class RestaurantsAdapter(val restaurants: List<Restaurant>) : RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.apply {
                name.text = restaurant.name
                rating.rating = restaurant.rating.toFloat()
                reviews.text = "${restaurant.review_count} reviews"
                address.text = restaurant.location.address1
                category.text = restaurant.categories[0].title
                distance.text = restaurant.toDistance()
                price.text = restaurant.price
                Glide.with(itemView)
                    .load(restaurant.image_url)
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(20)))
                    .into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRestaurantBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(restaurants[position])
}
