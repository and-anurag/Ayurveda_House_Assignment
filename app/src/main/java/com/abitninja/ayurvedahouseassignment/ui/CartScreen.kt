package com.abitninja.ayurvedahouseassignment.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abitninja.ayurvedahouseassignment.product.CartItem
import com.abitninja.ayurvedahouseassignment.viewmodel.ShopViewModel

@Composable
fun CartScreen(viewModel: ShopViewModel) {
    if (viewModel.cartItems.value.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Your cart is empty")
        }
    } else {
        LazyColumn {
            items(viewModel.cartItems.value.values.toList()) { cartItem ->
                CartItemRow(
                    cartItem = cartItem,
                    onIncreaseQuantity = { viewModel.addToCart(cartItem.product) },
                    onDecreaseQuantity = { viewModel.removeFromCart(cartItem.product) }
                )
            }

            item {
                val total = viewModel.cartItems.value.values.sumOf {
                    it.product.price * it.quantity
                }
                Text(
                    text = "Cart Total: ₹${String.format("%.2f", total)}",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            item {
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        imageVector = Icons.Default.Payments,
                        contentDescription = "Checkout"
                    )

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text ="Checkout"
                    )
                }
            }

        }
    }
}




@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(60.dp),
                    painter = painterResource(id = cartItem.product.imageId),
                    contentDescription = cartItem.product.name
                )

                Text(
                    text = "₹${cartItem.product.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onDecreaseQuantity) {
                    Icon(Icons.Default.Remove, "Decrease quantity")
                }
                Text(text = cartItem.quantity.toString())
                IconButton(onClick = onIncreaseQuantity) {
                    Icon(Icons.Default.Add, "Increase quantity")
                }
            }
        }
    }
}