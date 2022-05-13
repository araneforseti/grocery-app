package com.semblanceoffunctionality.grocery.ui.storelist

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.semblanceoffunctionality.grocery.R
import com.semblanceoffunctionality.grocery.adapters.StoreAdapter
import com.semblanceoffunctionality.grocery.databinding.FragmentStoreListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreListFragment : Fragment() {
    private val viewModel: StoreListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStoreListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = StoreAdapter()
        binding.storeList.adapter = adapter
        subscribeUi(adapter)

        binding.addItem.setOnClickListener {
            createStoreDialog(container)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item_list, menu)
    }

    private fun subscribeUi(adapter: StoreAdapter) {
        viewModel.stores.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }

    private fun createStoreDialog(container: ViewGroup?) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.fragment_new_store_dialog, container, false)
            builder.apply {
                setView(dialogView)
                setPositiveButton(R.string.create) { _, _ ->
                    val userInput = dialogView.findViewById<EditText>(R.id.new_store)
                    viewModel.addStore(userInput.text.toString())
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog?.cancel()
                }
            }
            builder.create()
        }?.show()
    }
}