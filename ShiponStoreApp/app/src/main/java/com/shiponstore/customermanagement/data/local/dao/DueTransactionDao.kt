package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shiponstore.customermanagement.data.local.entity.DueTransactionEntity
import kotlinx.coroutines.flow.Flow

/** Basic CRUD only in V1 - mirrors Due_Add sheet schema, no balance logic yet. */
@Dao
interface DueTransactionDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(dueTransaction: DueTransactionEntity): Long

    @Update
    suspend fun update(dueTransaction: DueTransactionEntity)

    @Query("DELETE FROM due_transactions WHERE localId = :localId")
    suspend fun deleteById(localId: Long)

    @Query("SELECT * FROM due_transactions WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<DueTransactionEntity>>

    @Query("SELECT * FROM due_transactions WHERE customerServerId = :customerServerId AND isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeForCustomer(customerServerId: String): Flow<List<DueTransactionEntity>>
}
