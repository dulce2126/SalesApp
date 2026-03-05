package com.example.sales.presentation.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sales.domain.model.Product
import com.example.sales.domain.usecase.product.ListProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.*


@HiltViewModel
class ListProductViewModel @Inject constructor(
    getProductsUseCase: ListProductsUseCase
) : ViewModel() {

    val uiState: StateFlow<ListProductUiState> =
        getProductsUseCase()
            .map { products ->
                ListProductUiState(
                    isLoading = false,
                    products = products
                )
            }
            .onStart {
                emit(ListProductUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListProductUiState()
            )
}


data class ListProductUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)
