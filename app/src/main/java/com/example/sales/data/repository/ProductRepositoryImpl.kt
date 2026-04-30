package com.example.sales.data.repository

import android.util.Log
import com.example.sales.data.local.datasource.ProductLocalDataSource
import com.example.sales.data.mapper.toDomain
import com.example.sales.data.mapper.toEntity

import com.example.sales.data.remote.datasource.ProductRemoteDataSource
import com.example.sales.data.remote.mapper.ProductRemoteMapper.toEntity
import com.example.sales.data.remote.mapper.ProductRemoteMapper.toDomain
import com.example.sales.data.remote.mapper.ProductRemoteMapper.toDto
import com.example.sales.domain.model.Product
import com.example.sales.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val remote: ProductRemoteDataSource,
    private val local: ProductLocalDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> = flow {

        //--- Intentar actualizar desde la API
        try {

            val remoteProducts = remote.getProducts()

            if(remoteProducts.success){
                val products = remoteProducts.data
                local.saveProducts(products.map { it.toEntity()})
            }

            //--- Emitir datos locales para evitar ciclos
            emitAll(
                local.getProducts()
                    .map { list -> list.map { it.toDomain() } }
            )
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error al sincronizar: ${e.message}")
            //--- aquí se puede manejar error (log, retry, etc.)
        }
    }.flowOn(Dispatchers.IO)

    //that means we´re seeing the products from the 2 different ways to save it, local and remote

    override suspend fun findProductByCode(productCode: String): Product? {

        // 1. Buscar local primero
        val localProduct = local.findProductByCode(productCode)
        if (localProduct != null) {
            return localProduct.toDomain()
        }

        // 2. Si no existe, buscar remoto
        return try {
            val remoteProduct = remote.findProductByCode(productCode)

            // guardar en local
            local.saveProduct(remoteProduct.toEntity())

            remoteProduct.toDomain()

        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveProduct(product: Product) {
        try {
            remote.saveProduct(product.toDto())
            local.saveProduct(product.toEntity())
        } catch (e: Exception) {
            local.saveProduct(product.toEntity())
        }
    }

    override suspend fun deleteProduct(productCode: String) {
        local.deleteProduct(productCode)
    }
}