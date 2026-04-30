package com.example.sales.di

import com.example.sales.data.repository.CustomerRepositoryImpl
import com.example.sales.data.repository.FirestoreCustomerRepository
import com.example.sales.data.repository.FirestoreProductRepository
import com.example.sales.data.repository.ProductRepositoryImpl
import com.example.sales.domain.repository.CustomerRepository
import com.example.sales.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    //donde se haga referencia, el objeto que va a crear es un InMemoryProductRepository
    //aquí es donde se hace la inyección
    //aqui se cambia dependiendo donde se quiere que se almacene ya sea en la
    //base de datos(RoomProductRepository) o (InMemoryProductRepository) o (FirestoreProductRepository)

    abstract fun bindProductRepository(
        repository: ProductRepositoryImpl//FirestoreProductRepository//RoomProductRepository
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        repository: CustomerRepositoryImpl//RoomCustomerRepository
    ): CustomerRepository
}

