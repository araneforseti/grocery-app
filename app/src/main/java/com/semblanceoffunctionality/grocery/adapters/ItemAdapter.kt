package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.ui.itemlist.ItemListFragment
import com.semblanceoffunctionality.grocery.data.Item
import com.semblanceoffunctionality.grocery.databinding.ListItemItemBinding
import com.semblanceoffunctionality.grocery.ui.itemlist.ItemListFragmentDirections

/**
 * Adapter for the [RecyclerView] in [ItemListFragment].
 */
class ItemAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListItemItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ItemViewHolder).bind(item)
    }

    class ItemViewHolder
        (
        private val binding: ListItemItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.item?.let { item ->
                    val action = ItemListFragmentDirections
                        .actionNavAllGroceriesToNavItemDetail(item.name)
                    it.findNavController().navigate(action)
                }
            }
        }

        fun bind(listItem: Item) {
            binding.apply {
                item = listItem
                executePendingBindings()
            }
        }
    }
}

private class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
