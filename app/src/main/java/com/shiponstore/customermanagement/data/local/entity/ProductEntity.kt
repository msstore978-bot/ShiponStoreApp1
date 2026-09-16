package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * PLACEHOLDER TABLE - NOT WIRED UP.
 *
 * The existing "Sales" sheet stores one Total_Bill amount per sale with no
 * per-product line items, so there is no current Product data to mirror.
 * Kept as an empty schema placeholder per PART 3; not used anywhere yet.
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    val serverId: String = "",
    val name: String = "",
    val unitPrice: Double = 0.0,
    val note: String = "",

    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean = false
)
