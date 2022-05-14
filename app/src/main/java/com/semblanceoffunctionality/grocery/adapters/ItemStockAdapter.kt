package com.semblanceoffunctionality.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.data.StockStatus
import com.semblanceoffunctionality.grocery.databinding.ListStoreStatusBinding
import com.semblanceoffunctionality.grocery.utilities.statusradio.StatusRadioButton
import com.semblanceoffunctionality.grocery.ui.itemdetail.ItemDetailFragment

/**
 * Adapter for the [RecyclerView] in [ItemDetailFragment].
 */
class ItemStockAdapter(
    private var stockedClickListener: StockedClickListener,
    private var unknownClickListener: UnknownClickListener,
    private var notStockedClickListener: NotStockedClickListener
) : ListAdapter<StockStatus, RecyclerView.ViewHolder>(StockStatusDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StockStatusViewHolder(
            ListStoreStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val status = getItem(position)

        (holder as StockStatusViewHolder).stockedStatus.setOnClickListener {
            stockedClickListener
        }
        holder.unknownStatus.setOnClickListener {
            unknownClickListener
        }
        holder.notStockedStatus.setOnClickListener {
            notStockedClickListener
        }

        holder.bind(status)
    }

    interface StockedClickListener {
        fun onClickListener(store: String)
    }

    interface UnknownClickListener {
        fun onClickListener(store: String)
    }

    interface NotStockedClickListener {
        fun onClickListener(store: String)
    }

    class StockStatusViewHolder(
        private val binding: ListStoreStatusBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var stockedStatus: StatusRadioButton = itemView.findViewById(R.id.status_stocked)
        var unknownStatus: StatusRadioButton = itemView.findViewById(R.id.status_unknown)
        var notStockedStatus: StatusRadioButton = itemView.findViewById(R.id.status_not_stocked)

        init {}

        fun bind(listStatus: StockStatus) {
            binding.apply {
                stockStatus = listStatus
                executePendingBindings()
            }
        }
    }
}

private class StockStatusDiffCallback : DiffUtil.ItemCallback<StockStatus>() {

    override fun areItemsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem.item == newItem.item && oldItem.store == newItem.store
    }

    override fun areContentsTheSame(oldItem: StockStatus, newItem: StockStatus): Boolean {
        return oldItem.item == newItem.item &&
                oldItem.store == newItem.store &&
                oldItem.stockStatus == newItem.stockStatus
    }
}
