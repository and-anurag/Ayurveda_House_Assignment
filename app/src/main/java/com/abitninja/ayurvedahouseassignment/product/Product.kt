package com.abitninja.ayurvedahouseassignment.product

// Data class for Product
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageId: Int
)

// Data class for Cart Item
data class CartItem(
    val product: Product,
    var quantity: Int = 0
)