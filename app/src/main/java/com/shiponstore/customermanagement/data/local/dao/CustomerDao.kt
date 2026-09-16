package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shiponstore.customermanagement.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(customer: CustomerEntity): Long

    @Update
    suspend fun update(customer: CustomerEntity)

    /** Hard delete - PART 6 asks that a delete *architecture* exists; this is it. */
    @Delete
    suspend fun delete(customer: CustomerEntity)

    @Query("DELETE FROM customers WHERE localId = :localId")
    suspend fun deleteById(localId: Long)

    @Query("SELECT * FROM customers WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customers WHERE localId = :localId LIMIT 1")
    suspend fun getById(localId: Long): CustomerEntity?

    @Query("SELECT * FROM customers WHERE serverId = :serverId LIMIT 1")
    suspend fun getByServerId(serverId: String): CustomerEntity?

    /**
     * Local search across Name / Shop_Name / Phone - mirrors what the
     * existing dashboard search box lets a user do, but works fully
     * offline against Room instead of Google Sheets (PART 6 / PART 7).
     */
    @Query(
        """
        SELECT * FROM customers
        WHERE isDeleted = 0
        AND (name LIKE '%' || :query || '%'
             OR shopName LIKE '%' || :query || '%'
             OR phone LIKE '%' || :query || '%')
        ORDER BY updatedAt DESC
        """
    )
    fun search(query: String): Flow<List<CustomerEntity>>

    @Query("SELECT COUNT(*) FROM customers WHERE isDeleted = 0")
    suspend fun count(): Int
}
