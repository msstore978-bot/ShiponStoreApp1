package com.shiponstore.customermanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Mirrors the existing "Reminders" sheet:
 * Reminder_ID | Cust_ID | Customer_Name | WhatsApp | Reminder_Date |
 * Reminder_Time | Reminder_DateTime | Status | Enabled | Sent_DateTime |
 * Sent_Count | Last_Message | Error_Message
 *
 * Schema only in V1 - AlarmManager / scheduler logic is explicitly out of
 * scope for this step (see PART 9).
 */
@Entity(
    tableName = "reminders",
    indices = [Index(value = ["serverId"], unique = true), Index(value = ["customerServerId"])]
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0L,

    val serverId: String,
    val customerServerId: String,
    val customerName: String = "",
    val whatsapp: String = "",
    val reminderDate: String = "",
    val reminderTime: String = "",
    val reminderDateTime: String = "",
    val status: String = "",
    val enabled: Boolean = true,
    val sentDateTime: String = "",
    val sentCount: Int = 0,
    val lastMessage: String = "",
    val errorMessage: String = "",

    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "syncStatus") val syncStatus: SyncStatus = SyncStatus.PENDING,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean = false
)
