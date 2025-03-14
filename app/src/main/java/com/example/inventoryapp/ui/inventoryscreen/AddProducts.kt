package com.example.inventoryapp.ui.inventoryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inventoryapp.crud.Product
import com.example.inventoryapp.crud.ProductRepository
import com.example.inventoryapp.crud.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProducts(navController: NavHostController, productRepository: ProductRepository) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
//    var category by remember { mutableStateOf(Category()) }

    val coroutineScope = rememberCoroutineScope()
    val productViewModel = ProductViewModel(productRepository)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Add Products",
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
                    .height(50.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            TextField(
                value = productPrice,
                onValueChange = { productPrice = it },
                label = { Text("Product Price") },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            TextField(
                value = productQuantity,
                onValueChange = { productQuantity = it },
                label = { Text("Product Quantity") },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            )
            Spacer(modifier = Modifier.height(16.dp))

            var isenabled by remember { mutableStateOf(false) }

            isenabled = productName.isNotEmpty()

            Button( enabled = isenabled,
                onClick = {
                    coroutineScope.launch {
                        val price = productPrice.toDoubleOrNull() ?: 0.0
                        val quantity = productQuantity.toIntOrNull() ?: 0
                        val newProduct = Product(name = productName, price = price, quantity = quantity)

//                        productRepository.insertProducts(newProduct)
                        productViewModel.insertProduct(newProduct)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE97451), disabledContainerColor = Color.Gray)
            ) {
                Text(modifier = Modifier,
                    color = Color(0xFFF0EAD6),
                    text = "Add Product")
            }
        }
    }
}