package com.example.sales.data.repository

import com.example.sales.domain.model.Product
import com.example.sales.domain.repository.ProductRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow


@Singleton
class InMemoryProductRepository @Inject constructor()
    : BaseInMemoryRepository<Product, String>(
    initialData = listOf(
        Product("P1", "Laptop", "Electronics", 15000.0, 10, true),
        Product("P2", "Mouse", "Electronics", 500.0, 50, true),
        Product("P3", "Desk", "Furniture", 3000.0, 5, false)
    )
),
    ProductRepository {

    override fun getId(item: Product): String = item.code

    override fun observeAll(): Flow<List<Product>> = state

    override suspend fun findProductByCode(productCode: String): Product? {
        return findById(productCode)
    }

    override suspend fun saveProduct(product: Product) {
        save(product)
    }

    override suspend fun deleteProduct(productCode: String) {
        deleteById(productCode)
    }

//devuelve muchos elementos por eso se pone flow
    override fun getProducts(): Flow<List<Product>> = observeAll()
}
