package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiponstore.customermanagement.data.local.entity.SupplierEntity

/** PLACEHOLDER - see SupplierEntity doc comment. Basic CRUD scaffold only. */
@Dao
interface SupplierDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(supplier: SupplierEntity): Long

    @Query("SELECT * FROM suppliers WHERE isDeleted = 0")
    suspend fun getAll(): List<SupplierEntity>
}
