package com.example.sales.data.remote.datasource

import com.example.sales.data.remote.api.ProductApiService
import com.example.sales.data.remote.dto.ProductDto
import com.example.sales.data.remote.dto.ProductResponse
import jakarta.inject.Inject


class ProductRemoteDataSource @Inject constructor(
    private val api: ProductApiService
) {

    suspend fun findProductByCode(code: String): ProductDto {
        return api.findProductByCode(code)
    }

    suspend fun saveProduct(product: ProductDto): ProductDto {
        return api.saveProduct(product)
    }

    suspend fun getProducts(): ProductResponse {
        return api.getProducts()
    }
}