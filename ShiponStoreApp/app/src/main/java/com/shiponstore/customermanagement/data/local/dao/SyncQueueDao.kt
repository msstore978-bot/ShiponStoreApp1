package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiponstore.customermanagement.data.local.entity.SyncQueueEntity

/**
 * Outbox DAO. Nothing in V1 calls this yet - it exists so the Sync Engine
 * step (V-next) has a ready-made table to enqueue into and drain from.
 */
@Dao
interface SyncQueueDao {
    @Insert(onConflict = OnConflictStrategy.APPEND)
    suspend fun enqueue(entry: SyncQueueEntity): Long

    @Query("SELECT * FROM sync_queue ORDER BY createdAt ASC")
    suspend fun getAllPending(): List<SyncQueueEntity>

    @Query("DELETE FROM sync_queue WHERE queueId = :queueId")
    suspend fun remove(queueId: Long)

    @Query("SELECT COUNT(*) FROM sync_queue")
    suspend fun count(): Int
}
