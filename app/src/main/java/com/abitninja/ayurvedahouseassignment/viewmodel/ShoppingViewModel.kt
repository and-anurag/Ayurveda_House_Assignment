package com.abitninja.ayurvedahouseassignment.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abitninja.ayurvedahouseassignment.R
import com.abitninja.ayurvedahouseassignment.product.CartItem
import com.abitninja.ayurvedahouseassignment.product.Product

class ShopViewModel : ViewModel() {
    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    // Change to mutableStateOf for better state management
    private val _cartItems = mutableStateOf<Map<Int, CartItem>>(emptyMap())
    val cartItems: State<Map<Int, CartItem>> = _cartItems

    private val _totalCartQuantity = mutableStateOf(0)
    val totalCartQuantity: State<Int> = _totalCartQuantity

    init {
        // Sample products data
        _products.addAll(
            listOf(
                Product(1, "MSI Modern 14 i3 13th Gen", 32990.0, "The MSI Modern 14 is powered by the latest 13th Gen Intel Core i3 processor, offering exceptional performance for everyday tasks. Its sleek aluminum body, vibrant 14-inch Full HD display, and lightweight design make it ideal for on-the-go productivity and entertainment. With fast SSD storage and long-lasting battery life, this laptop is perfect for students and professionals alike.", R.drawable.laptop),

                Product(2, "Samsung Galaxy M14", 12999.00, "The Samsung Galaxy M14 combines power and style with its robust Exynos processor, 50MP triple-camera setup, and a massive 6000mAh battery for all-day use. The 6.6-inch FHD+ display delivers an immersive viewing experience, while One UI Core ensures smooth performance. A perfect choice for multimedia enthusiasts and multitaskers.", R.drawable.smartphone),

                Product(3, "Sony WH-CH510 Wireless Headphones", 999.00, "Enjoy crystal-clear audio and up to 35 hours of wireless playback with the Sony WH-CH510 headphones. These lightweight, on-ear headphones are equipped with Bluetooth connectivity, swivel earcups for easy portability, and fast charging that gives you 90 minutes of playback with just 10 minutes of charge.", R.drawable.earphone_headphones),

                Product(4, "Fossil Grant Chronograph Watch", 1499.00, "The Fossil Grant Chronograph watch boasts a timeless design with Roman numerals and a blue dial housed in a stainless steel case. Its genuine leather strap and water resistance make it durable and elegant, perfect for both casual and formal occasions.", R.drawable.watch),

                Product(5, "Anker PowerPort Mini USB Charger", 299.00, "The Anker PowerPort Mini is a compact yet powerful USB charger designed for fast and efficient charging. It features dual USB ports to charge two devices simultaneously, making it ideal for travel or home use. Built with safety in mind, it includes surge protection and temperature control.", R.drawable.usb_charger),

                Product(6, "Amazfit Bip U Smartwatch", 1899.00, "The Amazfit Bip U smartwatch is packed with features like SpO2 monitoring, heart rate tracking, sleep analysis, and over 60 sports modes. Its lightweight design and 1.43-inch color display ensure all-day comfort and visibility. With up to 9 days of battery life, it's the perfect fitness and lifestyle companion.", R.drawable.smartwatch),

                Product(7, "Dell 24-inch S2421H Monitor", 8990.00, "The Dell S2421H is a 24-inch monitor with an IPS panel for vivid colors and wide viewing angles. Its 1080p Full HD resolution and built-in dual speakers deliver an excellent multimedia experience. The sleek design with ultra-thin bezels ensures a modern look and seamless multi-monitor setup.", R.drawable.monitor),

                Product(8, "Apple iPod Touch (7th Gen)", 3500.00, "The Apple iPod Touch (7th Gen) is a portable powerhouse for music, gaming, and apps. Featuring the A10 Fusion chip, a 4-inch Retina display, and support for Apple Music, it's perfect for entertainment on the go. Its lightweight design and long battery life ensure endless fun.", R.drawable.ipod),

                Product(9, "Canon EOS 90D DSLR Camera", 65000.00, "The Canon EOS 90D is a professional-grade DSLR camera with a 32.5 MP sensor and 4K video recording capabilities. It features fast autofocus, high-speed continuous shooting, and robust weather sealing for outdoor photography. Perfect for capturing stunning landscapes, portraits, and action shots.", R.drawable.camera)

            )
        )
    }

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableMap()
        val cartItem = currentItems[product.id]

        if (cartItem == null) {
            currentItems[product.id] = CartItem(product, 1)
        } else {
            currentItems[product.id] = cartItem.copy(quantity = cartItem.quantity + 1)
        }

        _cartItems.value = currentItems
        updateTotalQuantity()
    }

    fun removeFromCart(product: Product) {
        val currentItems = _cartItems.value.toMutableMap()
        val cartItem = currentItems[product.id]

        if (cartItem != null) {
            if (cartItem.quantity > 1) {
                currentItems[product.id] = cartItem.copy(quantity = cartItem.quantity - 1)
            } else {
                currentItems.remove(product.id)
            }
        }

        _cartItems.value = currentItems
        updateTotalQuantity()
    }

    private fun updateTotalQuantity() {
        _totalCartQuantity.value = _cartItems.value.values.sumOf { it.quantity }
    }
}