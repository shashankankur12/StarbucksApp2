package com.example.starbucksapp2.adaptor

import com.example.starbucksapp2.model.StoreModel
import com.example.starbucksapp2.R
import com.example.starbucksapp2.base.BaseAdapter
import com.example.starbucksapp2.databinding.ItemRestaurantBinding

class RestaurantAdaptor(list: List<StoreModel>, private val restaurantListener: RestaurantListener) : BaseAdapter<ItemRestaurantBinding, StoreModel>(list){

    override val layoutId: Int = R.layout.item_restaurant

    override fun bind(binding: ItemRestaurantBinding, item: StoreModel) {
        binding.apply {
            restaurant = item
            listener = restaurantListener
            executePendingBindings()
        }
    }
}

interface RestaurantListener {
    fun onRestaurantClicked(movie: StoreModel)
}