package com.example.inventoryapp.ui.inventoryscreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inventoryapp.crud.Product
import com.example.inventoryapp.crud.ProductRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProduct(navController: NavHostController, productId: Int, productRepository: ProductRepository) {
    var product by remember { mutableStateOf<Product?>(null) }
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        product = productRepository.getProductByID(productId)
        product?.let {
            productName = it.name
            productPrice = it.price.toString()
            productQuantity = it.quantity.toString()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Edit Product",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0EAD6)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name") },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
            )
            TextField(
                value = productPrice,
                onValueChange = { productPrice = it },
                label = { Text("Product Price") },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
            )
            TextField(
                value = productQuantity,
                onValueChange = { productQuantity = it },
                label = { Text("Product Quantity") },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        val price = productPrice.toDoubleOrNull() ?: 0.0
                        val quantity = productQuantity.toIntOrNull() ?: 0
                        val updatedProduct = product?.copy(name = productName, price = price, quantity = quantity)

                        updatedProduct?.let {
                            productRepository.updateProduct(it)
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
            ) {
                Text("Update Product")
            }
        }
    }
}