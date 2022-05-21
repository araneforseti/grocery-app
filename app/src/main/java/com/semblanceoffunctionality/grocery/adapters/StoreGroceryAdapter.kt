package com.semblanceoffunctionality.grocery.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.databinding.ListGroceryItemStatusBinding
import com.semblanceoffunctionality.grocery.ui.storegrocerylist.StoreGroceryFragment
import com.semblanceoffunctionality.grocery.ui.storegrocerylist.StoreGroceryFragmentDirections
import com.semblanceoffunctionality.grocery.ui.storegrocerylist.StoreGroceryItemsViewModel


class StoreGroceryAdapter() : ListAdapter<Map.Entry<Item, StockStatus>, StoreGroceryAdapter.ViewHolder>(
    StoreGroceryItemDiffCallback()
) {

    lateinit var groceryToggle: StoreGroceryFragment.ToggleObtainedCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListGroceryItemStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            groceryToggle
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitList(map: Map<Item, StockStatus>?) {
        super.submitList(map?.entries!!.toList())
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

        fun bind(item: Map.Entry<Item, StockStatus>) {
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

private class StoreGroceryItemDiffCallback : DiffUtil.ItemCallback<Map.Entry<Item, StockStatus>>() {

    override fun areItemsTheSame(
        oldItem: Map.Entry<Item, StockStatus>,
        newItem: Map.Entry<Item, StockStatus>
    ): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(
        oldItem: Map.Entry<Item, StockStatus>,
        newItem: Map.Entry<Item, StockStatus>
    ): Boolean {
        return oldItem == newItem
    }
}
