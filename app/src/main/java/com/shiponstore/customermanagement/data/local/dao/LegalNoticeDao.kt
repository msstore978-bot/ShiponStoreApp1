package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiponstore.customermanagement.data.local.entity.LegalNoticeEntity
import kotlinx.coroutines.flow.Flow

/** Basic CRUD only in V1 - mirrors Legal_Notices sheet schema. */
@Dao
interface LegalNoticeDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(notice: LegalNoticeEntity): Long

    @Query("DELETE FROM legal_notices WHERE localId = :localId")
    suspend fun deleteById(localId: Long)

    @Query("SELECT * FROM legal_notices WHERE isDeleted = 0 ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<LegalNoticeEntity>>
}
