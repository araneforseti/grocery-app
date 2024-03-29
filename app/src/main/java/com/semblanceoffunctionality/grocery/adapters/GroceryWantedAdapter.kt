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
import com.semblanceoffunctionality.grocery.databinding.ListGroceryCardBinding
import com.semblanceoffunctionality.grocery.ui.grocerylist.GroceriesFragment
import com.semblanceoffunctionality.grocery.ui.grocerylist.GroceriesFragmentDirections
import com.semblanceoffunctionality.grocery.ui.grocerylist.GroceryItemsViewModel

class GroceryWantedAdapter() :
    ListAdapter<Item, GroceryWantedAdapter.ViewHolder>(
        GroceryItemDiffCallback()
    ) {

    lateinit var groceryToggle: GroceriesFragment.ToggleObtainedCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_grocery_card,
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
        private val binding: ListGroceryCardBinding,
        groceryToggle: GroceriesFragment.ToggleObtainedCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.viewModel?.let { viewModel ->
                    val action = GroceriesFragmentDirections
                        .actionNavGroceriesToNavItemDetail(viewModel.name)
                    it.findNavController().navigate(action)
                }
            }
            binding.toggleObtained = groceryToggle
        }

        fun bind(item: Item) {
            with(binding) {
                viewModel = GroceryItemsViewModel(item)
                if((viewModel as GroceryItemsViewModel).obtained) {
                    this.itemName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    this.itemName.paintFlags = 0
                }
                executePendingBindings()
            }
        }
    }
}

private class GroceryItemDiffCallback : DiffUtil.ItemCallback<Item>() {

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
        return oldItem.equals(newItem)
    }
}
