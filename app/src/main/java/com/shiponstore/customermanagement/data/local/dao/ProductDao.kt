package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiponstore.customermanagement.data.local.entity.ProductEntity

/** PLACEHOLDER - see ProductEntity doc comment. Basic CRUD scaffold only. */
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(product: ProductEntity): Long

    @Query("SELECT * FROM products WHERE isDeleted = 0")
    suspend fun getAll(): List<ProductEntity>
}
