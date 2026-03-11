package com.example.sales.di

import com.example.sales.data.repository.InMemoryProductRepository
import com.example.sales.data.repository.RoomCustomerRepository
import com.example.sales.data.repository.RoomProductRepository
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
    //base de datos(RoomProductRepository) o (InMemoryProductRepository)

    abstract fun bindProductRepository(
        repository: RoomProductRepository
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCustomerRepository(
        repository: RoomCustomerRepository
    ): CustomerRepository
}

