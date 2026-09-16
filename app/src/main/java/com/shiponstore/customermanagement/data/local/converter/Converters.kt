package com.shiponstore.customermanagement.data.local.converter

import androidx.room.TypeConverter
import com.shiponstore.customermanagement.data.local.entity.SyncStatus

class Converters {
    @TypeConverter
    fun fromSyncStatus(value: SyncStatus): String = value.name

    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus =
        runCatching { SyncStatus.valueOf(value) }.getOrDefault(SyncStatus.PENDING)
}
