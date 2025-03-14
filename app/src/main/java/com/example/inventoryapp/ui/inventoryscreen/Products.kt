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
    var products by remember { mutableStateOf(listOf<Product>()) }

    val coroutineScope = rememberCoroutineScope()
    val productViewModel = ProductViewModel(productRepository)

    LaunchedEffect(Unit) {
        products = productRepository.getAllProducts()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Products",
                        color = Color(0xFFE97451)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF0EAD6)
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0EAD6)),
        ) {
            items(products) { product ->
                ProductCard(product, navController, productRepository) {
//                    coroutineScope.launch {
//                        products = productRepository.getAllProducts()
//                    }
                    productViewModel.getAllProducts()
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    navController: NavHostController,
    productRepository: ProductRepository,
    onDelete: () -> Unit,
) {
    val productViewModel = ProductViewModel(productRepository)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("edit_product/${product.id}")
            },
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
                Text(text = "Quantity: ${product.quantity}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFE97451))
            }

            IconButton(onClick = {
                onDelete()
//                coroutineScope.launch {
//                    productRepository.deleteProduct(product)
//                }
                productViewModel.deleteProduct(product)
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Product", tint = Color(0xFFE97451))
            }
        }
    }
}
