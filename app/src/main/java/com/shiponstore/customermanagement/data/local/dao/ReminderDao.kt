package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shiponstore.customermanagement.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

/** Basic CRUD only in V1 - mirrors Reminders sheet schema. No AlarmManager wiring (PART 9). */
@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(reminder: ReminderEntity): Long

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Query("DELETE FROM reminders WHERE localId = :localId")
    suspend fun deleteById(localId: Long)

    @Query("SELECT * FROM reminders WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE customerServerId = :customerServerId AND isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeForCustomer(customerServerId: String): Flow<List<ReminderEntity>>
}
