package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.ui.storelist.StoreListFragment
import com.semblanceoffunctionality.grocery.data.Store
import com.semblanceoffunctionality.grocery.databinding.StoreListItemBinding
import com.semblanceoffunctionality.grocery.ui.storelist.StoreListFragmentDirections

/**
 * Adapter for the [RecyclerView] in [StoreListFragment].
 */
class StoreAdapter : ListAdapter<Store, RecyclerView.ViewHolder>(StoreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StoreViewHolder(
            StoreListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val store = getItem(position)
        (holder as StoreViewHolder).bind(store)
    }

    class StoreViewHolder(
        private val binding: StoreListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.store?.let { store ->
                    val action = StoreListFragmentDirections
                        .actionNavStoreListToNavStoreDetail(store.name)
                    it.findNavController().navigate(action)
                }
            }
        }

        fun bind(listStore: Store) {
            binding.apply {
                store = listStore
                executePendingBindings()
            }
        }
    }
}

private class StoreDiffCallback : DiffUtil.ItemCallback<Store>() {

    override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
        return oldItem == newItem
    }
}
