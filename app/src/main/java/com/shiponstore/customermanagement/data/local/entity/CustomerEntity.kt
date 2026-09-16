package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Mirrors the existing "Customers" sheet, column for column, so a future
 * sync engine can map rows both ways without any renaming:
 *
 * Cust_ID | Name | Shop_Name | Phone | WhatsApp | Address | NID |
 * Opening_Due | Date | Current_Due | Profile_Pic | Note
 *
 * (see Code.gs -> setupSheets() for the authoritative column list)
 *
 * No business logic (due calculation, WhatsApp, etc.) lives here in V1 -
 * this is schema only, per PART 3.
 */
@Entity(
    tableName = "customers",
    indices = [Index(value = ["serverId"], unique = true)]
)
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    /** Maps to Cust_ID, e.g. "CUST-1234567890". Generated locally offline. */
    @ColumnInfo(name = "serverId")
    val serverId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "shopName")
    val shopName: String = "",

    @ColumnInfo(name = "phone")
    val phone: String = "",

    @ColumnInfo(name = "whatsapp")
    val whatsapp: String = "",

    @ColumnInfo(name = "address")
    val address: String = "",

    @ColumnInfo(name = "nid")
    val nid: String = "",

    @ColumnInfo(name = "openingDue")
    val openingDue: Double = 0.0,

    /** Kept as a display string (e.g. bn-BD date), same as the sheet does. */
    @ColumnInfo(name = "dateText")
    val dateText: String = "",

    @ColumnInfo(name = "currentDue")
    val currentDue: Double = 0.0,

    /** Local file path or Drive File ID - resolved to a viewable image later. */
    @ColumnInfo(name = "profilePicRef")
    val profilePicRef: String = "",

    @ColumnInfo(name = "note")
    val note: String = "",

    // --- Offline-first bookkeeping (PART 3) ---
    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "syncStatus")
    val syncStatus: SyncStatus = SyncStatus.PENDING,

    /** True once the row has been soft/hard removed locally (delete architecture, PART 6). */
    @ColumnInfo(name = "isDeleted")
    val isDeleted: Boolean = false
)
