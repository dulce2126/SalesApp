package com.example.sales.presentation.product.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel


@Composable
fun ListProductScreen(
    viewModel: ListProductViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.products.isEmpty() -> {
            EmptyProductsView()
        }

        else -> {
            ListProduct(products = uiState.products)
        }
    }
}