package com.semblanceoffunctionality.grocery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.semblanceoffunctionality.grocery.databinding.ActivityGroceryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGroceryBinding>(this, R.layout.activity_grocery)
    }
}
