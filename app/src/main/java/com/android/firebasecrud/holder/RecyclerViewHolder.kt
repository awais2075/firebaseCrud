package com.android.firebasecrud.holder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.android.firebasecrud._interface.ItemClickListener
import com.android.firebasecrud.model.Restaurant
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RecyclerViewHolder(itemView: View, itemClickListener: ItemClickListener<Any>, list: List<Any>) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            itemClickListener.onItemClicked(list[adapterPosition])
        }
    }

    fun bind(`object`: Any) {
        when (`object`) {
            is Restaurant -> {
                val restaurant: Restaurant = `object`
                itemView.textView_restaurantName.text = restaurant.restaurantName
                itemView.textView_restaurantAddress.text = restaurant.restaurantAddress
            }
        }

    }
}
