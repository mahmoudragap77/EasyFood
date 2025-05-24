package com.training.easyfood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val bottom_Nav =findViewById<BottomNavigationView>(R.id.btm_nav)

        val nav_controler = Navigation.findNavController(this,R.id.fragment_graph)

        NavigationUI.setupWithNavController(bottom_Nav,nav_controler)
    }
}