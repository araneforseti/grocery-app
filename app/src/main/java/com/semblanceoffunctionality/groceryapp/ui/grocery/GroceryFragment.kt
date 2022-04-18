package com.semblanceoffunctionality.groceryapp.ui.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.semblanceoffunctionality.groceryapp.R
import com.semblanceoffunctionality.groceryapp.databinding.FragmentGroceryBinding

class GroceryFragment : Fragment() {

    private var binding: FragmentGroceryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroceryBinding.inflate(inflater, container, false)
        val groceryViewModel =
            ViewModelProvider(this).get(GroceryViewModel::class.java)

        binding = FragmentGroceryBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}