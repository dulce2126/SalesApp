package com.example.sales.data.repository

import com.example.sales.data.remote.datasource.CustomerFirebaseDataSource
import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class FirestoreCustomerRepository @Inject constructor(
    private val firebaseDataSource: CustomerFirebaseDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> {
        return firebaseDataSource.getCustomers()
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        return firebaseDataSource.findCustomerByCode(customerCode)
    }

    override suspend fun saveCustomer(customer: Customer) {
        firebaseDataSource.saveCustomer(customer)
    }

    override suspend fun deleteCustomer(customerCode: String) {
        firebaseDataSource.deleteCustomer(customerCode)
    }
}