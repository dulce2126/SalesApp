package com.example.sales.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sales.data.local.dao.ProductDao
import com.example.sales.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SalesDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}