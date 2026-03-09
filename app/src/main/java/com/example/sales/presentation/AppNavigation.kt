package com.example.sales.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sales.presentation.product.create.CreateProductScreen
import com.example.sales.presentation.product.list.ListProductScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "product_list"
    ) {

        composable("product_list") {
            ListProductScreen(
                onAddProduct = {
                    navController.navigate("create_product")
                }
            )
        }

        composable("create_product") {
            CreateProductScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}