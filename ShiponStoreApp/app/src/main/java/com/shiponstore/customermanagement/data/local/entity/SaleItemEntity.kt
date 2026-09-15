package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * PLACEHOLDER TABLE - NOT WIRED UP.
 *
 * The existing Sales sheet has no line-item breakdown (see SaleEntity doc
 * comment). This table is scaffolding only, for a possible future itemized
 * sales feature - it is not referenced by any DAO logic or UI in V1.
 */
@Entity(
    tableName = "sale_items",
    indices = [Index(value = ["saleServerId"])]
)
data class SaleItemEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    val serverId: String = "",
    val saleServerId: String = "",
    val productServerId: String = "",
    val quantity: Double = 0.0,
    val unitPrice: Double = 0.0,
    val lineTotal: Double = 0.0,

    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING
)
