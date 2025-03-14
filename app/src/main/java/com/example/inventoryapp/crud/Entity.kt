package com.example.inventoryapp.crud

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products") // Ensure correct table name
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    val category: String
)


