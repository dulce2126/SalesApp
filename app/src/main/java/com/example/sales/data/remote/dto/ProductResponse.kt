package com.example.sales.data.remote.dto

data class ProductResponse (
    val success: Boolean,
    val data: List<ProductDto>
)