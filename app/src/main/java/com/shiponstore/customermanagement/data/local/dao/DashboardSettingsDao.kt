package com.shiponstore.customermanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiponstore.customermanagement.data.local.entity.DashboardSettingsEntity
import kotlinx.coroutines.flow.Flow

/** Single-row settings table mirroring Dashboard_Settings sheet. */
@Dao
interface DashboardSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(settings: DashboardSettingsEntity)

    @Query("SELECT * FROM dashboard_settings WHERE localId = 1 LIMIT 1")
    fun observe(): Flow<DashboardSettingsEntity?>

    @Query("SELECT * FROM dashboard_settings WHERE localId = 1 LIMIT 1")
    suspend fun get(): DashboardSettingsEntity?
}
