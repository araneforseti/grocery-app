package com.semblanceoffunctionality.grocery.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.ListGroceryItemStatusBinding
import com.semblanceoffunctionality.grocery.ui.storegrocerylist.StoreGroceryFragment
import com.semblanceoffunctionality.grocery.ui.storegrocerylist.StoreGroceryFragmentDirections
import com.semblanceoffunctionality.grocery.ui.storegrocerylist.StoreGroceryItemsViewModel
import com.semblanceoffunctionality.grocery.ui.storegrocerylist.StoreGroceryViewModel

class StoreGroceryAdapter() :
    ListAdapter<Item, StoreGroceryAdapter.ViewHolder>(
        StoreGroceryItemDiffCallback()
    ) {

    lateinit var groceryToggle: StoreGroceryFragment.ToggleObtainedCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_grocery_item_status,
                parent,
                false
            ),
            groceryToggle
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListGroceryItemStatusBinding,
        groceryToggle: StoreGroceryFragment.ToggleObtainedCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.viewModel?.let { viewModel ->
                    val action = StoreGroceryFragmentDirections
                        .actionNavStoreGroceryToNavItemDetail(viewModel.name)
                    it.findNavController().navigate(action)
                }
            }
            binding.toggleObtained = groceryToggle
        }

        fun bind(item: Item) {
            with(binding) {
                viewModel = StoreGroceryItemsViewModel(item)
                if((viewModel as StoreGroceryItemsViewModel).obtained) {
                    this.itemName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    this.itemName.paintFlags = 0
                }
                executePendingBindings()
            }
        }
    }
}

private class StoreGroceryItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem == newItem
    }
}
