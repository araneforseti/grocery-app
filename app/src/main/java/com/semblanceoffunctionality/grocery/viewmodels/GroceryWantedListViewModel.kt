package com.semblanceoffunctionality.grocery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.data.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroceryWantedListViewModel @Inject internal constructor(
    itemRepository: ItemRepository
) : ViewModel() {
    val items: LiveData<List<Item>> = itemRepository.getWantedItems().asLiveData()
}
