package com.semblanceoffunctionality.grocery.ui.grocerylist

import com.semblanceoffunctionality.grocery.data.Item

class GroceryWantedItemsViewModel(val item: Item) {
    val itemName
        get() = item.name
    val itemId
        get() = item.itemId
}
