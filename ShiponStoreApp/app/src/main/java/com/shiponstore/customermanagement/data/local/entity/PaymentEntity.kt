package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Mirrors the existing "Due_Payments" sheet (Pay Due menu):
 * Payment_ID | Date | Customer_ID | Previous_Due | Discount | Final_Payable |
 * Paid_Amount | Payment_Method | Remaining_Due
 */
@Entity(
    tableName = "payments",
    indices = [Index(value = ["serverId"], unique = true), Index(value = ["customerServerId"])]
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    val serverId: String,
    val customerServerId: String,
    val dateText: String = "",
    val previousDue: Double = 0.0,
    val discount: Double = 0.0,
    val finalPayable: Double = 0.0,
    val paidAmount: Double = 0.0,
    val paymentMethod: String = "",
    val remainingDue: Double = 0.0,

    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean = false
)
