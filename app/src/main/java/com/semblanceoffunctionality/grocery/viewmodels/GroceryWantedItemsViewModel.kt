package com.semblanceoffunctionality.grocery.viewmodels

import com.semblanceoffunctionality.grocery.data.Item

class GroceryWantedItemsViewModel(val item: Item) {
    val itemName
        get() = item.name
    val itemId
        get() = item.itemId
}
