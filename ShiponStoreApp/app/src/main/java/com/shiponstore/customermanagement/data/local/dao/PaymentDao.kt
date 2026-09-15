package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shiponstore.customermanagement.data.local.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow

/** Basic CRUD only in V1 - mirrors Due_Payments sheet schema, no payDue() logic yet. */
@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(payment: PaymentEntity): Long

    @Update
    suspend fun update(payment: PaymentEntity)

    @Query("DELETE FROM payments WHERE localId = :localId")
    suspend fun deleteById(localId: Long)

    @Query("SELECT * FROM payments WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payments WHERE customerServerId = :customerServerId AND isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeForCustomer(customerServerId: String): Flow<List<PaymentEntity>>
}
