package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiponstore.customermanagement.data.local.entity.SaleItemEntity

/** PLACEHOLDER - see SaleItemEntity doc comment. Basic CRUD scaffold only. */
@Dao
interface SaleItemDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(saleItem: SaleItemEntity): Long

    @Query("SELECT * FROM sale_items WHERE saleServerId = :saleServerId")
    suspend fun getForSale(saleServerId: String): List<SaleItemEntity>
}
