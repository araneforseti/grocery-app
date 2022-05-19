package com.semblanceoffunctionality.grocery.ui.grocerylist

import com.semblanceoffunctionality.grocery.data.Item

class GroceryItemsViewModel(val item: Item) {
    val itemName
        get() = item.name
    val name
        get() = item.name
    val quantity
        get() = "0"
    val obtained
        get() = false
}
