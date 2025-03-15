package com.example.inventoryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.inventoryapp.ui.dashboardscreen.DashboardScreen
import com.example.inventoryapp.ui.theme.InventoryAppTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProvideWindowInsets {
                InventoryAppTheme {
                    DashboardScreen()
                }
            }
        }
    }
}