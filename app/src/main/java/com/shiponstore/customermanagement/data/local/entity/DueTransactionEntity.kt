package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Mirrors the existing "Due_Add" sheet (বাকির খাতা — add due directly):
 * Add_ID | Date | Customer_ID | Amount | Note
 */
@Entity(
    tableName = "due_transactions",
    indices = [Index(value = ["serverId"], unique = true), Index(value = ["customerServerId"])]
)
data class DueTransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    val serverId: String,
    val customerServerId: String,
    val dateText: String = "",
    val amount: Double = 0.0,
    val note: String = "",

    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean = false
)
