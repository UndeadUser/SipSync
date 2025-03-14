package com.example.inventoryapp.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    fun insertProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.insertProducts(product)
        }
    }

    fun getProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = productRepository.getProductByID(id)
        }
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val product = productRepository.getAllProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteProduct(product)
        }
    }

    fun deleteALlProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteAllProducts()
        }
    }
}