package com.example.sales.data.repository

import android.util.Log
import com.example.sales.data.local.datasource.CustomerLocalDataSource
import com.example.sales.data.mapper.toDomain
import com.example.sales.data.mapper.toEntity
import com.example.sales.data.remote.datasource.CustomerRemoteDataSource
import com.example.sales.data.remote.mapper.CustomerRemoteMapper.toDomain
import com.example.sales.data.remote.mapper.CustomerRemoteMapper.toDto
import com.example.sales.data.remote.mapper.CustomerRemoteMapper.toEntity
import com.example.sales.domain.model.Customer
import com.example.sales.domain.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val remote: CustomerRemoteDataSource,
    private val local: CustomerLocalDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> = flow {

        //--- Intentar actualizar desde la API
        try {

            val remoteCustomers = remote.getCustomers()
            Log.d("Datos recuperados", remoteCustomers.data.size.toString())

            if(remoteCustomers.success){
                val customers = remoteCustomers.data
                local.saveCustomers(customers.map { it.toEntity()})
                Log.d("Datos recuperados", "you did it")
            }

            //--- Emitir datos locales para evitar ciclos
            emitAll(
                local.getCustomers()
                    .map { list -> list.map { it.toDomain() } }
            )
        } catch (e: Exception) {
            Log.e("CustomerRepository", "Error al sincronizar: ${e.message}")
            //--- aquí se puede manejar error (log, retry, etc.)
        }
    }.flowOn(Dispatchers.IO)

    //that means we´re seeing the Customers from the 2 different ways to save it, local and remote

    override suspend fun findCustomerByCode(customerCode: String): Customer? {

        // 1. Buscar local primero
        val localCustomer = local.findCustomerByCode(customerCode)
        if (localCustomer != null) {
            return localCustomer.toDomain()
        }

        // 2. Si no existe, buscar remoto
        return try {
            val remoteCustomer = remote.findCustomerByCode(customerCode)

            // guardar en local
            local.saveCustomer(remoteCustomer.toEntity())

            remoteCustomer.toDomain()

        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveCustomer(customer: Customer) {
        try {
            remote.saveCustomer(customer.toDto())
            local.saveCustomer(customer.toEntity())
        } catch (e: Exception) {
            local.saveCustomer(customer.toEntity())
        }
    }

    override suspend fun deleteCustomer(customerCode: String) {
        local.deleteCustomer(customerCode)
    }
}