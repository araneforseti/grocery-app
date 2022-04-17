package com.semblanceoffunctionality.groceryapp.ui.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.semblanceoffunctionality.groceryapp.R
import com.semblanceoffunctionality.groceryapp.databinding.FragmentGroceryBinding

class GroceryFragment : Fragment() {

    private var _binding: FragmentGroceryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val groceryViewModel =
            ViewModelProvider(this).get(GroceryViewModel::class.java)

        _binding = FragmentGroceryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}