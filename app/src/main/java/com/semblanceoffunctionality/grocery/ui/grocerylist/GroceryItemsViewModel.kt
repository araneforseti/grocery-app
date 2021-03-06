package com.semblanceoffunctionality.grocery.ui.grocerylist

import com.semblanceoffunctionality.grocery.data.Item

class GroceryItemsViewModel(val item: Item) {
    val name
        get() = item.name
    val quantity: String
        get() = item.quantityString()
    val obtained
        get() = item.obtained
}
