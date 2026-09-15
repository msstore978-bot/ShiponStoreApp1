package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * PLACEHOLDER TABLE - NOT WIRED UP.
 *
 * PART 3 of the V1 spec asked for a Supplier table. However, analysis of
 * the uploaded project (Code.gs / Index.html / Index-1.html) found no
 * Supplier sheet, no Supplier Apps Script function, and no Supplier menu
 * in the existing UI - "Supplier-related functionality" does not exist
 * in the current system yet.
 *
 * This entity is created only as an empty schema placeholder, exactly as
 * PART 3 allows ("পূর্ণ business logic implement করার প্রয়োজন নেই").
 * No DAO method beyond basic CRUD is implemented, nothing references this
 * table from the UI, and it will stay unused until a real Supplier feature
 * is designed together with the user in a later step.
 */
@Entity(tableName = "suppliers")
data class SupplierEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    val serverId: String = "",
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val note: String = "",

    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean = false
)
