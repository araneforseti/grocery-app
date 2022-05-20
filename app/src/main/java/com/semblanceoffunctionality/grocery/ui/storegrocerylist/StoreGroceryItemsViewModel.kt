package com.semblanceoffunctionality.grocery.ui.storegrocerylist

import com.semblanceoffunctionality.grocery.data.Item

class StoreGroceryItemsViewModel(val item: Item) {
    val name
        get() = item.name
    val quantity: String
        get() = item.quantityString()
    val obtained
        get() = item.obtained
}
