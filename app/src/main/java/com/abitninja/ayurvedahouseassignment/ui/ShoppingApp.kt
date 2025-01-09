package com.abitninja.ayurvedahouseassignment.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abitninja.ayurvedahouseassignment.viewmodel.ShopViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingApp() {
    val navController = rememberNavController()
    val viewModel: ShopViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping App") },
                actions = {
                    CartIcon(
                        quantity = viewModel.totalCartQuantity.value,
                        onClick = { navController.navigate("cart") }
                    )
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "products",
            modifier = Modifier.padding(padding)
        ) {
            composable("products") {
                ProductListScreen(
                    viewModel = viewModel,
                    onProductClick = { productId ->
                        navController.navigate("product/$productId")
                    }
                )
            }
            composable(
                "product/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
                ProductDetailScreen(
                    viewModel = viewModel,
                    productId = productId
                )
            }
            composable("cart") {
                CartScreen(viewModel = viewModel)
            }
        }
    }
}