package com.shiponstore.customermanagement.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A generic outbox table: whenever any offline-first entity is created,
 * edited, or deleted, a row is meant to land here describing "what changed"
 * so a future Sync Engine can replay it against the Apps Script API.
 *
 * V1 does not implement a sync engine, so nothing reads this table yet -
 * it exists purely as the foundation described in PART 3 / architecture
 * diagram (Local Database -> Sync Engine -> Apps Script API -> Sheets).
 */
@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true)
    val queueId: Long = 0L,

    /** e.g. "customers", "sales", "due_transactions", "payments", "reminders" */
    val entityTable: String,

    /** The serverId (business ID) of the affected row. */
    val entityServerId: String,

    /** "CREATE" | "UPDATE" | "DELETE" */
    val operation: String,

    val createdAt: Long = System.currentTimeMillis(),

    val attempts: Int = 0,

    val lastError: String = ""
)
