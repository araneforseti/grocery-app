package com.semblanceoffunctionality.grocery.ui.storegrocerylist

import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.data.StockStatus

class StoreGroceryItemsViewModel(
    private val itemEntry: Map.Entry<Item, StockStatus>
    ) {
    val name
        get() = itemEntry.key.name
    val item
        get() = itemEntry.key
    val quantity: String
        get() = itemEntry.key.quantity.toString()
    val obtained
        get() = itemEntry.key.obtained
    val stockStatus
        get() = itemEntry.value
}
