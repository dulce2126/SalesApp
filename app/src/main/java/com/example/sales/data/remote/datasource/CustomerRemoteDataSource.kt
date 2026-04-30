package com.example.sales.data.remote.datasource

import com.example.sales.data.remote.api.CustomerApiService
import com.example.sales.data.remote.dto.CustomerDto
import com.example.sales.data.remote.dto.CustomerResponse
import jakarta.inject.Inject

class CustomerRemoteDataSource @Inject constructor(
    private val api: CustomerApiService
) {

    suspend fun findCustomerByCode(code: String): CustomerDto {
        return api.findCustomerById(code)
    }

    suspend fun saveCustomer(customer: CustomerDto): CustomerDto {
        return api.saveCustomer(customer)
    }

    suspend fun getCustomers(): CustomerResponse {
        return api.getCustomers()
    }
}