package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shiponstore.customermanagement.data.local.entity.SaleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Basic CRUD only in V1 - addSale() business logic (recalculating due,
 * WhatsApp messages) is intentionally not ported yet (PART 3 / PART 9).
 */
@Dao
interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(sale: SaleEntity): Long

    @Update
    suspend fun update(sale: SaleEntity)

    @Query("DELETE FROM sales WHERE localId = :localId")
    suspend fun deleteById(localId: Long)

    @Query("SELECT * FROM sales WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<SaleEntity>>

    @Query("SELECT * FROM sales WHERE customerServerId = :customerServerId AND isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeForCustomer(customerServerId: String): Flow<List<SaleEntity>>
}
