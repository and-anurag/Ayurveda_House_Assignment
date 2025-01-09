package com.abitninja.ayurvedahouseassignment.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abitninja.ayurvedahouseassignment.product.CartItem
import com.abitninja.ayurvedahouseassignment.product.Product
import com.abitninja.ayurvedahouseassignment.viewmodel.ShopViewModel
import kotlinx.coroutines.launch


@Composable
fun ProductListScreen(
    viewModel: ShopViewModel,
    onProductClick: (Int) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(
            items = viewModel.products,
            key = { product -> product.id }
        ) { product ->
            ProductItem(
                product = product,
                cartItem = viewModel.cartItems.value[product.id],
                onAddToCart = { viewModel.addToCart(product) },
                onRemoveFromCart = { viewModel.removeFromCart(product) },
                onClick = { onProductClick(product.id) }
            )
        }
    }
}


@Composable
fun CartIcon(quantity: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Box {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
            if (quantity > 0) {
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(text = quantity.toString())
                }
            }
        }
    }
}



@Composable
fun ProductItem(
    product: Product,
    cartItem: CartItem?,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            Text(
                modifier = Modifier.padding(8.dp),
                text = product.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = product.imageId),
                contentDescription = product.name
            )

            Text(
                modifier = Modifier.padding(8.dp),
                text = "₹${product.price}",
                style = MaterialTheme.typography.bodyLarge
            )

            if (cartItem == null) {
                Button(
                    onClick = {
                        scope.launch {
                            onAddToCart()
                            Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show()
                        }
                    }

                ) {
                    Text("Add to Cart")
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = onRemoveFromCart) {
                        Icon(Icons.Default.Remove, "Decrease quantity")
                    }
                    Text(text = cartItem.quantity.toString())
                    IconButton(onClick = onAddToCart) {
                        Icon(Icons.Default.Add, "Increase quantity")
                    }
                }
            }
        }
    }
}




@Composable
fun ProductDetailScreen(
    viewModel: ShopViewModel,
    productId: Int
) {
    val product = viewModel.products.find { it.id == productId }
    val cartItem = viewModel.cartItems.value[productId]  // Fixed this line to access .value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    product?.let {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            item {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium
                )

                Image(
                    painter = painterResource(id = product.imageId),
                    contentDescription = product.name
                )

                Text(
                    text = "₹${product.price}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (cartItem == null) {
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.addToCart(product)
                                Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Add to Cart")
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = { viewModel.removeFromCart(product) }) {
                            Icon(Icons.Default.Remove, "Decrease quantity")
                        }
                        Text(text = cartItem.quantity.toString())
                        IconButton(onClick = { viewModel.addToCart(product) }) {
                            Icon(Icons.Default.Add, "Increase quantity")
                        }
                    }
                }

            }

        }

    }
}

