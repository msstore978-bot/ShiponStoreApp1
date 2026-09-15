package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiponstore.customermanagement.data.local.entity.ExpenseEntity

/** PLACEHOLDER - see ExpenseEntity doc comment. Basic CRUD scaffold only. */
@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(expense: ExpenseEntity): Long

    @Query("SELECT * FROM expenses WHERE isDeleted = 0")
    suspend fun getAll(): List<ExpenseEntity>
}
