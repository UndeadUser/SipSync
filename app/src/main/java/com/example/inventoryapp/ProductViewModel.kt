package com.example.inventoryapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            productRepository.insertProducts(product)
        }
    }

    fun getProductById(id: Int) {
        viewModelScope.launch {
            val product = productRepository.getProductByID(id)
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            val product = productRepository.getAllProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productRepository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productRepository.deleteProduct(product)
        }
    }

    fun deleteALlProducts() {
        viewModelScope.launch {
            productRepository.deleteAllProducts()
        }
    }
}