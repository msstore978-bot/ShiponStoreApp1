package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Mirrors the existing "Dashboard_Settings" sheet, which only ever holds
 * one data row:
 * Logo_URL | Business_Name | Proprietor_Name | Address | Mobile_Number
 */
@Entity(tableName = "dashboard_settings")
data class DashboardSettingsEntity(
    @PrimaryKey
    val localId: Long = 1L, // singleton row

    val logoRef: String = "",
    val businessName: String = "আমার দোকান",
    val proprietorName: String = "",
    val address: String = "",
    val mobileNumber: String = "",

    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING
)
