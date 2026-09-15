package com.shiponstore.customermanagement.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shiponstore.customermanagement.data.local.converter.Converters
import com.shiponstore.customermanagement.data.local.dao.CustomerDao
import com.shiponstore.customermanagement.data.local.dao.DashboardSettingsDao
import com.shiponstore.customermanagement.data.local.dao.DueTransactionDao
import com.shiponstore.customermanagement.data.local.dao.ExpenseDao
import com.shiponstore.customermanagement.data.local.dao.LegalNoticeDao
import com.shiponstore.customermanagement.data.local.dao.PaymentDao
import com.shiponstore.customermanagement.data.local.dao.ProductDao
import com.shiponstore.customermanagement.data.local.dao.ReminderDao
import com.shiponstore.customermanagement.data.local.dao.SaleDao
import com.shiponstore.customermanagement.data.local.dao.SaleItemDao
import com.shiponstore.customermanagement.data.local.dao.SupplierDao
import com.shiponstore.customermanagement.data.local.dao.SyncQueueDao
import com.shiponstore.customermanagement.data.local.entity.CustomerEntity
import com.shiponstore.customermanagement.data.local.entity.DashboardSettingsEntity
import com.shiponstore.customermanagement.data.local.entity.DueTransactionEntity
import com.shiponstore.customermanagement.data.local.entity.ExpenseEntity
import com.shiponstore.customermanagement.data.local.entity.LegalNoticeEntity
import com.shiponstore.customermanagement.data.local.entity.PaymentEntity
import com.shiponstore.customermanagement.data.local.entity.ProductEntity
import com.shiponstore.customermanagement.data.local.entity.ReminderEntity
import com.shiponstore.customermanagement.data.local.entity.SaleEntity
import com.shiponstore.customermanagement.data.local.entity.SaleItemEntity
import com.shiponstore.customermanagement.data.local.entity.SupplierEntity
import com.shiponstore.customermanagement.data.local.entity.SyncQueueEntity

/**
 * V1 Android Offline Foundation database.
 *
 * This is purely a local store (PART 3 / PART 4) - there is no network
 * code anywhere in this class or its DAOs. The eventual Sync Engine
 * (Local DB -> Sync Engine -> Apps Script API -> Google Sheets) is a
 * separate, later component that will read/write through these same DAOs;
 * it is intentionally not implemented in V1.
 */
@Database(
    entities = [
        CustomerEntity::class,
        SaleEntity::class,
        DueTransactionEntity::class,
        PaymentEntity::class,
        ReminderEntity::class,
        LegalNoticeEntity::class,
        DashboardSettingsEntity::class,
        SyncQueueEntity::class,
        // Forward-looking placeholders only (see each entity's doc comment) -
        // these do not correspond to any feature in the current system yet.
        SupplierEntity::class,
        ProductEntity::class,
        SaleItemEntity::class,
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun saleDao(): SaleDao
    abstract fun dueTransactionDao(): DueTransactionDao
    abstract fun paymentDao(): PaymentDao
    abstract fun reminderDao(): ReminderDao
    abstract fun legalNoticeDao(): LegalNoticeDao
    abstract fun dashboardSettingsDao(): DashboardSettingsDao
    abstract fun syncQueueDao(): SyncQueueDao
    abstract fun supplierDao(): SupplierDao
    abstract fun productDao(): ProductDao
    abstract fun saleItemDao(): SaleItemDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private const val DATABASE_NAME = "shipon_store.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    // No destructive fallback in V1: we want data loss to be
                    // impossible to hit silently while the schema is still new.
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
