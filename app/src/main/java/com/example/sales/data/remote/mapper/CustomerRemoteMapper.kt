package com.example.sales.data.remote.mapper

import com.example.sales.data.local.entity.CustomerEntity
import com.example.sales.data.remote.dto.CustomerDto
import com.example.sales.domain.model.Customer

object CustomerRemoteMapper {

    fun CustomerDto.toDomain(): Customer = Customer(
        id, name, email, purchaseHistory
    )

    fun CustomerDto.toEntity(): CustomerEntity = CustomerEntity(
        id, name, email, purchaseHistory
    )

    fun Customer.toDto(): CustomerDto = CustomerDto(
        code, name, email, purchaseHistory
    )
}