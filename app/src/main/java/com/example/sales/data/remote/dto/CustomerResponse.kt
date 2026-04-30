package com.example.sales.data.remote.dto

data class CustomerResponse (
    val success: Boolean,
    val data: List<CustomerDto>
)