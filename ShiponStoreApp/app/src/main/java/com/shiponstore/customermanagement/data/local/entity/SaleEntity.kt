package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Mirrors the existing "Sales" sheet:
 * Sale_ID | Date | Customer_ID | Invoice_No | Total_Bill | Previous_Due |
 * Total_Payable | Paid_Amount | Payment_Method | New_Due | Invoice_Img
 *
 * Schema only in V1 - addSale() business logic (balance recalculation,
 * WhatsApp messages) is not implemented here yet.
 */
@Entity(
    tableName = "sales",
    indices = [Index(value = ["serverId"], unique = true), Index(value = ["customerServerId"])]
)
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    val serverId: String,
    val customerServerId: String,
    val dateText: String = "",
    val invoiceNo: String = "",
    val totalBill: Double = 0.0,
    val previousDue: Double = 0.0,
    val totalPayable: Double = 0.0,
    val paidAmount: Double = 0.0,
    val paymentMethod: String = "",
    val newDue: Double = 0.0,
    val invoiceImgRef: String = "",

    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean = false
)
