package com.example.inventoryapp.ui.inventoryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inventoryapp.crud.Product
import com.example.inventoryapp.crud.ProductRepository
import com.example.inventoryapp.crud.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Products(navController: NavHostController, productRepository: ProductRepository) {
    val productViewModel = remember { ProductViewModel(productRepository) }
    val products by productViewModel.products.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Carbonated", "Juice", "Alcohol")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Products", color = Color(0xFFE97451))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(0xFFE97451)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF0EAD6)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0EAD6))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                categories.forEach { category ->
                    Button(
                        onClick = { selectedCategory = category },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == category) Color(0xFFE97451) else Color(0xFFF4C6B2),
                            contentColor = Color(0xFFF0EAD6)
                        )
                    ) {
                        Text(category)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                val filteredProducts = if (selectedCategory == "All") {
                    products
                } else {
                    products.filter { it.category == selectedCategory }
                }
                items(filteredProducts) { product ->
                    ProductCard(product, navController, productViewModel)
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    navController: NavHostController,
    productViewModel: ProductViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("edit_product/${product.id}") },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EAD6))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium, color = Color(0xFFE97451))
                Text(text = "Price: ₱${product.price}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFE97451))
                Text(text = "Quantity: x${product.quantity}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFE97451))
                Text(text = "Category: ${product.category}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFE97451))
            }

            IconButton(onClick = {
                productViewModel.deleteProduct(product)
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Product", tint = Color(0xFFE97451))
            }
        }
    }
}
