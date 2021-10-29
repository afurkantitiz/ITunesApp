package com.example.casestudy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.casestudy.R
import com.example.casestudy.databinding.ActivityMainBinding
import com.example.casestudy.utils.gone
import com.example.casestudy.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = Navigation.findNavController(this, R.id.navHostFragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    fun hideNavigationBar() {
        binding.bottomNavigationView.gone()
    }

    fun showNavigationBar() {
        binding.bottomNavigationView.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}